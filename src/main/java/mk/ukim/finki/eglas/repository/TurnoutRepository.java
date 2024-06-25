package mk.ukim.finki.eglas.repository;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.CandidatesElectionRealization;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.model.PollingStation;
import mk.ukim.finki.eglas.model.Turnout;
import mk.ukim.finki.eglas.model.views.TotalTurnoutByMunicipality;
import mk.ukim.finki.eglas.records.TotalCandidacyResults;
import mk.ukim.finki.eglas.records.TotalListResultsPerPollingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TurnoutRepository extends JpaRepository<Turnout, Long>, JpaSpecificationExecutor<Turnout> {
    @Query(value = "SELECT (SELECT COUNT(v) FROM Turnout v WHERE v.electionRealization = :ri) * 100.0 / (SELECT COUNT(c) FROM Citizen c WHERE c.dateOfBirth <= :dateThreshold) FROM ElectionRealization ri WHERE ri = :ri")
    Double turnOutByRealization(@Param("ri") ElectionRealization electionRealization, @Param("dateThreshold") LocalDate dateThreshold);

    @Query("SELECT NEW mk.ukim.finki.eglas.records.TotalCandidacyResults(kan, COUNT(distinct cv), null) " +
            "FROM Candidate k " +
            "LEFT JOIN Candidacy kan ON kan.candidate = k AND kan.candidatesElectionRealization = :realization " +
            "LEFT JOIN CandidacyVote cv ON cv.candidacy = kan " +
            "WHERE kan is not null " +
            "GROUP BY kan")
    List<TotalCandidacyResults> countVotesByCitizenAndRealization(@Param("realization") CandidatesElectionRealization realization);

    @Query("select tr from TotalTurnoutByMunicipality tr where tr.map_id = :mapId and tr.realizationId = :realizationId")
    List<TotalTurnoutByMunicipality> turnOutByMunicipality(@Param("mapId") String mapId, @Param("realizationId") Long realizationId);


    @Query(value = "SELECT (SELECT COUNT(v) FROM Turnout v WHERE v.electionRealization.id = :realizationId and v.pollingStation.id = :pollingStationId) * 100.0 / (SELECT COUNT(c) FROM Citizen c join Address a on c.address = a WHERE c.dateOfBirth <= :dateThreshold and a.pollingStation.id = :pollingStationId) FROM ElectionRealization ri WHERE ri.id = :realizationId")
    Double turnOutByRealizationAndMunicipalityAndPollingStation(@Param("realizationId") Long realizationId, @Param("pollingStationId") Long pollingStationId, @Param("dateThreshold") LocalDate dateThreshold);

//    Turnout getTurnoutByPollingStationAndAndElectionRealization(PollingStation pollingStation, ElectionRealization electionRealization);

    @Modifying
    @Transactional
    @Query(value = "refresh materialized view turnout_by_municipality", nativeQuery = true)
    void refreshTurnoutByMunicipalityView();

    @Query("select count(distinct t.id) > 0 from Turnout t where t.citizen.id = :citizenId and t.electionRealization.id = :realizationId")
    Boolean hasCitizenVotedOnRealization(@Param("citizenId") Long citizenId, @Param("realizationId") Long realizationId);

    @Query(value = "select kl.kl_id as list_name, p.p_ime as participant, im_id as polling_station_id, res.vote_count as vote_count " +
            "from kandidatski_listi kl " +
            "    join public.partii p on p.p_id = kl.p_id " +
            "         left join get_view_for_lists_realizations(:realizationId) res on res.kl_id = kl.kl_id and res.im_id = :pollingStationId " +
            "where kl.ri_id = :realizationId", nativeQuery = true)
    List<Map<String, Object>> totalListResultsPerPollingStation(
            @Param("realizationId") Long realizationId,
            @Param("pollingStationId") Long pollingStationId);

    @Query(value = "select kl.kl_id as list_name, p.p_ime as participant, res.o_id as municipality_id, res.vote_count as vote_count " +
            "from kandidatski_listi kl " +
            "    join public.partii p on p.p_id = kl.p_id " +
            "         left join get_view_for_lists_realizations_by_municipalities(:realizationId) res on res.gl_id = kl.kl_id and res.o_id = :municipalityId " +
            "where kl.ri_id = :realizationId", nativeQuery = true)
    List<Map<String, Object>> totalListResultsPerMunicipality(
            @Param("realizationId") Long realizationId,
            @Param("municipalityId") Long municipalityId);

    @Query(value = "select kan.kan_id as candidacy_id, (g.g_ime || ' ' || g.g_prezime) participant, res.im_id as polling_station_id, res.vote_count as vote_count " +
            "from kandidaturi kan " +
            "    join gragjani g on g.g_id = kan.g_id " +
            "left join get_view_for_candidates_realizations(1) res on res.kan_id = kan.kan_id and res.im_id = :pollingStationId " +
            "where kan.ri_id = :realizationId", nativeQuery = true)
    List<Map<String, Object>> totalCandidacyResultsPerPollingStation(
            @Param("realizationId") Long realizationId,
            @Param("pollingStationId") Long pollingStationId);

    @Query(value = "select kan.kan_id as candidacy_id, (g.g_ime || ' ' || g.g_prezime) as participant, res.o_id as municipality_id, res.vote_count as vote_count " +
            "from kandidaturi kan " +
            "    join gragjani g on g.g_id = kan.g_id " +
            "left join get_view_for_candidates_realizations_by_municipalities(:realizationId) res on res.kan_id = kan.kan_id and res.o_id = :municipalityId " +
            "where kan.ri_id = :realizationId", nativeQuery = true)
    List<Map<String, Object>> totalCandidacyResultsPerMunicipality(
            @Param("realizationId") Long realizationId,
            @Param("municipalityId") Long municipalityId);
//    List<TotalListResultsPerPollingStation> totalListResultsPerPollingStation(@Param("realizationId") Long realizationId,
//                                                                              @Param("pollingStationId") Long pollingStationId);
}