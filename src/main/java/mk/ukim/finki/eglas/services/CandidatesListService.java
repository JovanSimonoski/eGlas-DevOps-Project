package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.CandidatesList;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CandidatesListService {
    CandidatesList findById(Long id);
    List<CandidatesList> findAll();
    CandidatesList update(Long id, String description, Long partyId, Long candidatesListElectionRealizationId, Long municipalityId, Long electoralUnitId, List<Long> candidatesInList);

    void delete(Long id);

    void addCandidateToCandidatesList(Long candidatesListId, Long candidateId);
}
