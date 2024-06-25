package mk.ukim.finki.eglas.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.beans.Transient;
import java.lang.annotation.Native;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into kandidati (g_id) values (:id)", nativeQuery = true)
    void insertCandidate(@Param("id") Long id);
}
