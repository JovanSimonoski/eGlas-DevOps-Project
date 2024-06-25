package mk.ukim.finki.eglas.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.CandidacyVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidacyVoteRepository extends JpaRepository<CandidacyVote, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into glasovi_za_kandidat (ug_id, kan_id) values (:voteId, :candidacyId)", nativeQuery = true)
    void voteForCandidate(@Param("voteId") Long voteId,
                          @Param("candidacyId") Long candidacyId);
}
