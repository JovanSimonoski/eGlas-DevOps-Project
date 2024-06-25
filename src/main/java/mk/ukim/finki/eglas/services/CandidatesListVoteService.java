package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.CandidatesListVote;

import java.util.List;

public interface CandidatesListVoteService {
    CandidatesListVote findById(Long id);
    List<CandidatesListVote> findAll();
    CandidatesListVote update(Long id, Long CandidateListId);
    void delete(Long id);
}
