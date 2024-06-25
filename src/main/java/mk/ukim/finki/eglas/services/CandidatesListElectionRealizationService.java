package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import mk.ukim.finki.eglas.model.CandidatesListElectionRealization;

import java.util.List;

public interface CandidatesListElectionRealizationService {
    CandidatesListElectionRealization findById(Long id);
    List<CandidatesListElectionRealization> findAll();
    CandidatesListElectionRealization update(Long id);
    void delete(Long id);
}
