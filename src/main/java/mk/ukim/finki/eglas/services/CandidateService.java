package mk.ukim.finki.eglas.services;


import mk.ukim.finki.eglas.model.Candidate;

import java.util.List;

public interface CandidateService {
    Candidate findById(Long id);
    List<Candidate> findAll();
    Candidate update(Long id);
    void delete(Long id);
}
