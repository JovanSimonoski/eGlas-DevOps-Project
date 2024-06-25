package mk.ukim.finki.eglas.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.CandidatesListElectionRealization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidatesListElectionRealizationRepository extends JpaRepository<CandidatesListElectionRealization, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into realizacii_so_kandidatska_lista (ri_id) values (:id)", nativeQuery = true)
    void insertCandidateListElections(@Param("id") Long id);

        @Query(value = "select count(*) > 0 from CandidatesListElectionRealization cler where cler.id = :realizationId")
        boolean existsCandidateListElections(@Param("realizationId") Long realizationId);
}
