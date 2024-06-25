package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import mk.ukim.finki.eglas.model.CandidatesListElectionRealization;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.repository.CandidatesListElectionRealizationRepository;
import mk.ukim.finki.eglas.services.CandidatesListElectionRealizationService;
import mk.ukim.finki.eglas.services.ElectionRealizationService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CandidatesListElectionRealizationServiceImpl implements CandidatesListElectionRealizationService {
    private final CandidatesListElectionRealizationRepository repository;
    private final ElectionRealizationService electionRealizationService;

    CandidatesListElectionRealizationServiceImpl(CandidatesListElectionRealizationRepository repository,
                                                 ElectionRealizationService electionRealizationService){
        this.repository = repository;
        this.electionRealizationService = electionRealizationService;
    }

    public CandidatesListElectionRealization findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No candidate list election realization found"));
    }
    public List<CandidatesListElectionRealization> findAll(){
        return repository.findAll();
    }

    public CandidatesListElectionRealization update(Long id){
        CandidatesListElectionRealization can = (CandidatesListElectionRealization) electionRealizationService.findById(id);
        return repository.save(can);
    }

    public void delete(Long id){
        repository.delete(findById(id));
    }
}
