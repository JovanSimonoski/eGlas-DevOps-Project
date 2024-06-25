package mk.ukim.finki.eglas.services.Impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.*;
import mk.ukim.finki.eglas.repository.CandidacyVoteRepository;
import mk.ukim.finki.eglas.repository.VoteRepository;
import mk.ukim.finki.eglas.services.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final TurnoutService turnoutService;
    private final CandidacyVoteRepository candidacyVoteRepository;
    private final AddressService addressService;
    private final VoteIdentificationCodeService voteIdentificationCodeService;
    private final ElectionRealizationService electionRealizationService;
    private final CandidacyService candidacyService;
    private final CitizenService citizenService;

    public VoteServiceImpl(VoteRepository voteRepository, CandidacyVoteService candidacyVoteService, TurnoutService turnoutService, AddressService addressService, CandidatesElectionRealizationService candidatesElectionRealizationService, CandidacyVoteRepository candidacyVoteRepository, VoteIdentificationCodeService voteIdentificationCodeService, ElectionRealizationService electionRealizationService, CandidacyService candidacyService, CitizenService citizenService) {
        this.voteRepository = voteRepository;
        this.turnoutService = turnoutService;
        this.addressService = addressService;
        this.candidacyVoteRepository = candidacyVoteRepository;
        this.voteIdentificationCodeService = voteIdentificationCodeService;
        this.electionRealizationService = electionRealizationService;
        this.candidacyService = candidacyService;
        this.citizenService = citizenService;
    }

    @Override
    public Vote vote(UUID voteIdentificationCodeId, Long pollingStationId, Long realizationId) {
        PollingStation pollingStation = addressService.findPollingStationById(pollingStationId);
        VoteIdentificationCode voteIdentificationCode = voteIdentificationCodeService.findById(voteIdentificationCodeId);
        ElectionRealization electionRealization = electionRealizationService.findById(realizationId);

        Vote vote = new Vote();
        vote.setVoteTimestamp(LocalDateTime.now());
        vote.setPollingStation(pollingStation);
        vote.setVoteIdentificationCode(voteIdentificationCode);
        vote.setElectionRealization(electionRealization);

        return voteRepository.save(vote);
    }

    @Override
    @Transactional
    public CandidacyVote voteForCandidate(Long citizenId, UUID voteIdentificationCodeId, Long realizationId, Long id){

        Candidacy candidacy = candidacyService.findById(id);
        VoteIdentificationCode voteIdentificationCode = voteIdentificationCodeService.findById(voteIdentificationCodeId);
        ElectionRealization electionRealization = electionRealizationService.findById(realizationId);
        Citizen citizen = citizenService.findById(citizenId);
        PollingStation pollingStation = citizen.getAddress().getPollingStation();
        CandidacyVote candidacyVote = new CandidacyVote();
        candidacyVote.setVoteTimestamp(LocalDateTime.now());
        candidacyVote.setVoteIdentificationCode(voteIdentificationCode);
        candidacyVote.setElectionRealization(electionRealization);
        candidacyVote.setPollingStation(pollingStation);
        candidacyVote.pollingStation = pollingStation;
        candidacyVote.setCandidacy(candidacy);

        turnoutService.update(null, LocalDateTime.now(), citizenId, realizationId, pollingStation);

        return candidacyVoteRepository.save(candidacyVote);
    }
}
