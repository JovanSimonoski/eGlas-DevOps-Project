package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.PollingStation;
import mk.ukim.finki.eglas.model.dto.CitizenVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByIdNum(String idNum);
    @Query(value = "select count(distinct g_embg) from gragjani where age(now(), g_datum_ragjanje) > interval '18 years'", nativeQuery = true)
    Integer availableVoters();
    List<Citizen> findAllByDocumentIsNotNullAndAddress_PollingStation_Id(Long pollingStationId);
    @Query("select new mk.ukim.finki.eglas.model.dto.CitizenVote(c, t.voteTimestamp) from Citizen c left join Turnout t on t.citizen.id = c.id where c.address.pollingStation.id = :pollingStationId")
    List<CitizenVote> findAllByAddress_PollingStation(@Param("pollingStationId") Long pollingStationId);

}
