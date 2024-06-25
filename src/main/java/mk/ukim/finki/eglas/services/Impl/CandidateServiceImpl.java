package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Candidate;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.repository.CandidateRepository;
import mk.ukim.finki.eglas.services.CandidateService;
import mk.ukim.finki.eglas.services.CitizenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository repository;
    private final CitizenService citizenService;

    CandidateServiceImpl(CandidateRepository repository, CitizenService citizenService){
        this.repository = repository;
        this.citizenService = citizenService;
    }

    @Override
    public Candidate findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No candidate found"));
    }

    @Override
    public List<Candidate> findAll() {
        return repository.findAll();
    }

    @Override
    public Candidate update(Long id) {
        Citizen citizen = citizenService.findById(id);
        Candidate candidate = new Candidate();
        candidate.setId(id);
//        candidate.setName(citizen.getName());
//        candidate.setSurname(citizen.getSurname());
//        candidate.setAddress(citizen.getAddress());
//        candidate.setIdNum(citizen.getIdNum());
//        candidate.setDocument(citizen.getDocument());
//        candidate.setDateOfBirth(citizen.getDateOfBirth());
        repository.insertCandidate(id);
        return candidate;
    }

    @Override
    public void delete(Long id) {
        Candidate candidate = (Candidate) citizenService.findById(id);
        repository.delete(candidate); ;
    }
}
