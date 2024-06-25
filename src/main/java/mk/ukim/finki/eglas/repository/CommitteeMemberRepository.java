package mk.ukim.finki.eglas.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.CommitteeMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommitteeMemberRepository extends JpaRepository<CommitteeMember, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into clenovi_na_komisija (g_id) values (:id)", nativeQuery = true)
    void insertCommitteeMember(@Param("id") Long id);
}
