package mk.ukim.finki.eglas.services;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.Candidacy;
import mk.ukim.finki.eglas.model.CandidacyVote;
import mk.ukim.finki.eglas.model.Vote;
import mk.ukim.finki.eglas.model.VoteIdentificationCode;

import java.util.UUID;

public interface VoteService  {
    Vote vote(UUID voteIdentificationCodeId, Long pollingStationId, Long realizationId);

    @Transactional
    CandidacyVote voteForCandidate(Long citizenId, UUID voteIdentificationCodeId, Long realizationId, Long id);
}
