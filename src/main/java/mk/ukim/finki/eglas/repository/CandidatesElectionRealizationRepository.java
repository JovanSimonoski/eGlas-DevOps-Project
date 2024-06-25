package mk.ukim.finki.eglas.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatesElectionRealizationRepository extends JpaRepository<CandidatesElectionRealization, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into realizacii_so_kandidaturi (ri_id) values (:id)", nativeQuery = true)
    void insertCandidacyElections(@Param("id") Long id);

//    @Query(value = "select distinct elections from CandidatesElectionRealization elections join Candidacy candidacy on candidacy.candidatesElectionRealization = elections left join Vote vote on vote.electionRealization = elections and vote.citizen != :citizen where elections.date >= current_date and (vote.citizen is null or vote.citizen != :citizen) and (candidacy.municipality is null or candidacy.municipality = :municipality)")
    @Query(value = "select distinct elections from CandidatesElectionRealization elections join Candidacy candidacy on candidacy.candidatesElectionRealization = elections where elections.date >= current_date and elections not in (select v.electionRealization from Turnout     v where v.citizen = :citizen) and (candidacy.municipality is null or candidacy.municipality = :municipality)")
    List<CandidatesElectionRealization> availableElections(@Param("citizen") Citizen citizen,
                                                           @Param("municipality") Municipality municipality);
}
