package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.CandidacyVote;

import java.util.List;
import java.util.UUID;

public interface CandidacyVoteService {
    List<CandidacyVote> findAll();
    CandidacyVote findById(Long id);
    CandidacyVote update(Long voteId, Long candidacyId);
    CandidacyVote delete(Long id);
    void voteForCandidate(Long citizenId, Long realizationId, UUID voteId, Long id);
}
