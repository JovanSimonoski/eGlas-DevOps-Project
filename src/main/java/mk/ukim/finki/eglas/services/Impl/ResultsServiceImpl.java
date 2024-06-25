package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.model.dto.ResultsDto;
import mk.ukim.finki.eglas.records.TotalCandidacyResultsPerMunicipality;
import mk.ukim.finki.eglas.records.TotalCandidacyResultsPerPollingStation;
import mk.ukim.finki.eglas.records.TotalListResultsPerMunicipality;
import mk.ukim.finki.eglas.records.TotalListResultsPerPollingStation;
import mk.ukim.finki.eglas.repository.CandidatesListElectionRealizationRepository;
import mk.ukim.finki.eglas.repository.TurnoutRepository;
import mk.ukim.finki.eglas.services.CandidatesListElectionRealizationService;
import mk.ukim.finki.eglas.services.ElectionRealizationService;
import mk.ukim.finki.eglas.services.MunicipalityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResultsServiceImpl {

    private final ElectionRealizationService electionRealizationService;
    private final CandidatesListElectionRealizationService candidatesListElectionRealizationService;
    private final MunicipalityService municipalityService;
    private final TurnoutRepository turnoutRepository;
    private final CandidatesListElectionRealizationRepository candidatesListElectionRealizationRepository;

    public ResultsServiceImpl(ElectionRealizationService electionRealizationService, CandidatesListElectionRealizationService candidatesListElectionRealizationService, MunicipalityService municipalityService, TurnoutRepository turnoutRepository, CandidatesListElectionRealizationRepository candidatesListElectionRealizationRepository) {
        this.electionRealizationService = electionRealizationService;
        this.candidatesListElectionRealizationService = candidatesListElectionRealizationService;
        this.municipalityService = municipalityService;
        this.turnoutRepository = turnoutRepository;
        this.candidatesListElectionRealizationRepository = candidatesListElectionRealizationRepository;
    }

    public List<ResultsDto> getResultsForRealizationBy(Long realizationId, Long municipalityId, Long pollingStationId) {
        ElectionRealization electionRealization = electionRealizationService.findById(realizationId);
        if(pollingStationId != null) {
            if(isListRealization(realizationId)) {
                List<TotalListResultsPerPollingStation> totalListResultsPerPollingStation = listResultsPerPollingStation(2L, 2L);
//                List<TotalListResultsPerPollingStation> totalListResultsPerPollingStation = listResultsPerPollingStation(2L, 2L);
                Long totalVotes = totalListResultsPerPollingStation.stream().mapToLong(x -> Long.parseLong(x.getVoteCount())).sum();
                return totalListResultsPerPollingStation.stream().map(x -> {
                    ResultsDto resultsDto = new ResultsDto();
                    resultsDto.setId(Long.parseLong(x.getPollingStationId()));
                    resultsDto.setVotesCount(Long.parseLong(x.getVoteCount()));
                    resultsDto.setParticipantName(x.getPartyName());
                    resultsDto.setVotesPercentage((Long.parseLong(x.getVoteCount()) * 100.0) / totalVotes);
                    return resultsDto;
                }).toList();
            }
            else {
                    List<TotalCandidacyResultsPerPollingStation> totalCandidacyResultsPerPollingStations = candidacyResultsPerPollingStations(realizationId, municipalityId);
                    Long totalVotes = totalCandidacyResultsPerPollingStations.stream().mapToLong(x -> Long.parseLong(x.getVoteCount())).sum();
                    return totalCandidacyResultsPerPollingStations.stream().map(x -> {
                        ResultsDto resultsDto = new ResultsDto();
                        resultsDto.setId(Long.parseLong(x.getCandidacyId()));
                        resultsDto.setVotesCount(Long.parseLong(x.getVoteCount() != null ? x.getVoteCount() : "0"));
                        resultsDto.setParticipantName(x.getCitizenName());
                        resultsDto.setVotesPercentage((Long.parseLong(x.getVoteCount()) * 100.0) / totalVotes);
                        return resultsDto;
                    }).toList();
            }

        }
        else {
            if(isListRealization(realizationId)) {
                List<TotalListResultsPerMunicipality> totalListResultsPerMunicipalities = listResultsPerMuncipality(realizationId, municipalityId);
                Long totalVotes = totalListResultsPerMunicipalities.stream().mapToLong(x -> Long.parseLong(x.getVoteCount())).sum();
                return totalListResultsPerMunicipalities.stream().map(x -> {
                    ResultsDto resultsDto = new ResultsDto();
                    resultsDto.setId(Long.parseLong(x.getMunicipalityId()));
                    resultsDto.setVotesCount(Long.parseLong(x.getVoteCount() != null ? x.getVoteCount() : "0"));
                    resultsDto.setParticipantName(x.getPartyName());
                    resultsDto.setVotesPercentage((Long.parseLong(x.getVoteCount()) * 100.0) / totalVotes);
                    return resultsDto;
                }).toList();
            }
            else {
                List<TotalCandidacyResultsPerMunicipality> totalCandidacyResultsPerMunicipalities = candidacyResultsPerMunicipalities(realizationId, municipalityId);
                Long totalVotes = totalCandidacyResultsPerMunicipalities.stream().mapToLong(x -> Long.parseLong(x.getVoteCount())).sum();
                return totalCandidacyResultsPerMunicipalities.stream().map(x -> {
                    ResultsDto resultsDto = new ResultsDto();
                    resultsDto.setId(Long.parseLong(x.getCandidacyId()));
                    resultsDto.setVotesCount(Long.parseLong(x.getVoteCount() != null ? x.getVoteCount() : "0"));
                    resultsDto.setParticipantName(x.getCitizenName());
                    resultsDto.setVotesPercentage((Long.parseLong(x.getVoteCount()) * 100.0) / totalVotes);
                    return resultsDto;
                }).toList();
            }
        }
    }

    private boolean isListRealization(Long realizationId) {
        return candidatesListElectionRealizationRepository.existsCandidateListElections(realizationId);
    }

    List<TotalListResultsPerPollingStation> listResultsPerPollingStation(Long realizationId, Long pollingStationId) {
        List<Map<String, Object>> results = turnoutRepository.totalListResultsPerPollingStation(realizationId, pollingStationId);
        return results.stream().map(x -> {
            TotalListResultsPerPollingStation totalListResultsPerPollingStation = new TotalListResultsPerPollingStation();
            totalListResultsPerPollingStation.setListName((x.get("list_name").toString()));
            totalListResultsPerPollingStation.setPartyName((String) x.get("participant").toString());
            totalListResultsPerPollingStation.setVoteCount((String) x.get("vote_count").toString());
            totalListResultsPerPollingStation.setPollingStationId((String) x.get("polling_station_id").toString());
            return totalListResultsPerPollingStation;
        }).toList();
    }

    List<TotalListResultsPerMunicipality> listResultsPerMuncipality(Long realizationId, Long municipalityId) {
        List<Map<String, Object>> results = turnoutRepository.totalListResultsPerMunicipality(realizationId, municipalityId);
        return results.stream().map(x -> {
            TotalListResultsPerMunicipality totalListResultsPerMunicipality = new TotalListResultsPerMunicipality();
            totalListResultsPerMunicipality.setListName((x.get("list_name").toString()));
            totalListResultsPerMunicipality.setPartyName(x.get("participant") != null ? x.get("participant").toString() : null);
            totalListResultsPerMunicipality.setVoteCount(x.get("vote_count") != null ? x.get("vote_count").toString() : "0");
            totalListResultsPerMunicipality.setMunicipalityId(x.get("municipality_id") != null ? x.get("vote_count").toString() : "0");
            return totalListResultsPerMunicipality;
        }).toList();
    }

    List<TotalCandidacyResultsPerPollingStation> candidacyResultsPerPollingStations (Long realizationId, Long pollingStationId) {
        List<Map<String, Object>> results = turnoutRepository.totalCandidacyResultsPerPollingStation(realizationId, pollingStationId);
        return results.stream().map(x -> {
            TotalCandidacyResultsPerPollingStation totalCandidacyResultsPerPollingStationa = new TotalCandidacyResultsPerPollingStation();
            totalCandidacyResultsPerPollingStationa.setCandidacyId((x.get("candidacy_id").toString()));
            totalCandidacyResultsPerPollingStationa.setCitizenName((String) x.get("participant").toString());
            totalCandidacyResultsPerPollingStationa.setVoteCount((String) x.get("vote_count").toString());
            totalCandidacyResultsPerPollingStationa.setPollingStationId((String) x.get("polling_station_id").toString());
            return totalCandidacyResultsPerPollingStationa;
        }).toList();
    }

    List<TotalCandidacyResultsPerMunicipality> candidacyResultsPerMunicipalities (Long realizationId, Long municipalityId) {
        List<Map<String, Object>> results = turnoutRepository.totalCandidacyResultsPerMunicipality(realizationId, municipalityId);
        return results.stream().map(x -> {
            TotalCandidacyResultsPerMunicipality totalCandidacyResultsPerMunicipality = new TotalCandidacyResultsPerMunicipality();
            totalCandidacyResultsPerMunicipality.setCandidacyId((x.get("candidacy_id").toString()));
            totalCandidacyResultsPerMunicipality.setCitizenName((String) x.get("participant").toString());
            totalCandidacyResultsPerMunicipality.setVoteCount(x.get("vote_count") != null ? x.get("vote_count").toString() : "0");
            totalCandidacyResultsPerMunicipality.setMunicipalityId(x.get("municipality_id") != null ? x.get("vote_count").toString() : "0");
            return totalCandidacyResultsPerMunicipality;
        }).toList();
    }

}
