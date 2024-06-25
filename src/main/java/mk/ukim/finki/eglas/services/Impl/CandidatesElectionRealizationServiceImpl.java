package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.repository.CandidatesElectionRealizationRepository;
import mk.ukim.finki.eglas.services.CandidatesElectionRealizationService;
import mk.ukim.finki.eglas.services.CitizenService;
import mk.ukim.finki.eglas.services.ElectionRealizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatesElectionRealizationServiceImpl implements CandidatesElectionRealizationService {

    private final CandidatesElectionRealizationRepository repository;
    private final ElectionRealizationService electionRealizationService;
    @Autowired
    private CitizenService citizenService;

    CandidatesElectionRealizationServiceImpl(CandidatesElectionRealizationRepository repository,
                                             ElectionRealizationService electionRealizationService){
        this.repository = repository;
        this.electionRealizationService = electionRealizationService;
    }

    @Override
    public CandidatesElectionRealization findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No candidate election realization found"));
    }

    @Override
    public List<CandidatesElectionRealization> findAll(){
        return repository.findAll();
    }
    @Override
    public CandidatesElectionRealization update(Long id){
        CandidatesElectionRealization candidate = (CandidatesElectionRealization) electionRealizationService.findById(id);
        return repository.save(candidate);
    }
    @Override
    public void delete(Long id){
        repository.delete(findById(id));
    }

    @Override
    public List<CandidatesElectionRealization> findAvailable(Long citizenId) {
        Citizen citizen = citizenService.findById(citizenId);
        return repository.availableElections(citizen, citizen.getAddress().getMunicipality());
    }
}
