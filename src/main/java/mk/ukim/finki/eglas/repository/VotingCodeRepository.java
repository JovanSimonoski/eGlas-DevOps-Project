package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.VotingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VotingCodeRepository extends JpaRepository<VotingCode, Long> {
    VotingCode findByCode(String code);
    Optional<VotingCode> findByCitizen_IdAndElectionRealization_Id(Long citizenId, Long electionRealizationId);
    @Query("select vc.citizen from VotingCode vc where vc.code = :code")
    Optional<Citizen> findCitizenByCode(@Param("code") String code);
}
