package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import java.util.List;
public interface CandidatesElectionRealizationService {
    CandidatesElectionRealization findById(Long id);
    List<CandidatesElectionRealization> findAll();
    CandidatesElectionRealization update(Long id);
    void delete(Long id);

    List<CandidatesElectionRealization> findAvailable(Long citizenId);
}
