package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.PollingStation;
import mk.ukim.finki.eglas.model.Turnout;
import mk.ukim.finki.eglas.model.views.TotalTurnoutByMunicipality;
import mk.ukim.finki.eglas.records.TotalCandidacyResults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TurnoutService {
    List<Turnout> findAll();
    Turnout findById(Long id);
    Turnout update(Long id, LocalDateTime voteTimestamp, Long citizenId, Long electionRealizationId, PollingStation pollingStation);
    Turnout delete(Long id);

    Double turnOutByElectionRealization(Long id);

    Double getTurnOutByRealizationAndMunicipality(Long realizationId, String map_id);

    Double getTurnOutByRealizationAndMunicipalityAndPollingStation(Long realizationId, String map_id, Long pollingStationId);

    List<TotalCandidacyResults> resultsByCandidateElectionsRealization(Long realizationId);

    Boolean hasCitizenVotedOnRealization(Long citizenId, Long realizationId);
}
