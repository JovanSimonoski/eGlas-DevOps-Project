package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.*;
import mk.ukim.finki.eglas.model.views.TotalTurnoutByMunicipality;
import mk.ukim.finki.eglas.records.TotalCandidacyResults;
import mk.ukim.finki.eglas.repository.TurnoutRepository;
import mk.ukim.finki.eglas.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TurnoutServiceImpl implements TurnoutService {

    private final ElectionRealizationService electionRealizationService;
    private final CitizenService citizenService;
    private final TurnoutRepository turnoutRepository;
    private final AddressService addressService;

    @Autowired
    private CandidatesElectionRealizationService candidatesElectionRealizationService;

    public TurnoutServiceImpl(ElectionRealizationService electionRealizationService, CitizenService citizenService, TurnoutRepository turnoutRepository, AddressService addressService) {
        this.electionRealizationService = electionRealizationService;
        this.citizenService = citizenService;
        this.turnoutRepository = turnoutRepository;
        this.addressService = addressService;
    }

    @Override
    public List<Turnout> findAll() {
        return turnoutRepository.findAll();
    }

    @Override
    public Turnout findById(Long id) {
        return turnoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Vote not found"));
    }

    @Override
    public Turnout update(Long id, LocalDateTime voteTimestamp, Long citizenId, Long electionRealizationId, PollingStation pollingStation) {
        Turnout vote = new Turnout();
        Citizen citizen = citizenService.findById(citizenId);
        ElectionRealization electionRealization = electionRealizationService.findById(electionRealizationId);
        if(id != null)
        {
            vote = findById(id);
        }
        vote.setVoteTimestamp(voteTimestamp);
        vote.setCitizen(citizen);
        vote.setElectionRealization(electionRealization);
        vote.setPollingStation(pollingStation);
        return turnoutRepository.save(vote);
    }

    @Override
    public Turnout delete(Long id) {
        Turnout vote = findById(id);
        turnoutRepository.delete(vote);
        return vote;
    }

    @Override
    public Double turnOutByElectionRealization(Long id)
    {
        ElectionRealization electionRealization = electionRealizationService.findById(id);
        return turnoutRepository.turnOutByRealization(electionRealization, LocalDate.now().minusYears(18));
    }

    @Override
    public Double getTurnOutByRealizationAndMunicipality(Long realizationId, String map_id) {
        if (map_id == null) {
           return turnOutByElectionRealization(realizationId);
        }
        return turnoutRepository.turnOutByMunicipality(map_id, realizationId).get(0).getTotal();
    }

    @Override
    public Double getTurnOutByRealizationAndMunicipalityAndPollingStation(Long realizationId, String map_id, Long pollingStationId) {
        if (map_id == null && pollingStationId == null) {
            return turnOutByElectionRealization(realizationId);
        }

        if (pollingStationId == null) {
            return getTurnOutByRealizationAndMunicipality(realizationId,map_id);
        }
        LocalDate dateThreshold = LocalDate.now().minusYears(18);
        return turnoutRepository.turnOutByRealizationAndMunicipalityAndPollingStation(realizationId, pollingStationId, LocalDate.now().plusYears(180));
    }


    @Override
    public List<TotalCandidacyResults> resultsByCandidateElectionsRealization(Long realizationId)
    {
        CandidatesElectionRealization candidatesElectionRealization = candidatesElectionRealizationService.findById(realizationId);
        List<TotalCandidacyResults> votes = turnoutRepository.countVotesByCitizenAndRealization(candidatesElectionRealization);
        Long totalVotes = votes.stream().mapToLong(TotalCandidacyResults::voteCount).sum();
        return votes.stream()
                .map(x -> new TotalCandidacyResults(x.candidacy(), x.voteCount(), x.voteCount() * 100.0 / totalVotes))
                .sorted(Comparator.comparing(TotalCandidacyResults::voteCount).reversed())
                .toList();
    }

    @Override
    public Boolean hasCitizenVotedOnRealization (Long citizenId, Long realizationId)
    {
        return turnoutRepository.hasCitizenVotedOnRealization(citizenId, realizationId);
    }
}
