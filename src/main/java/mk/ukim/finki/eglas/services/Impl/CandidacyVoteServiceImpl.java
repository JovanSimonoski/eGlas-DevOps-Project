package mk.ukim.finki.eglas.services.Impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.*;
import mk.ukim.finki.eglas.repository.CandidacyVoteRepository;
import mk.ukim.finki.eglas.services.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CandidacyVoteServiceImpl implements CandidacyVoteService {

    private final CandidacyVoteRepository repository;
    private final TurnoutService turnoutService;
    private final CandidacyService candidacyService;
    private final CitizenService citizenService;
    private final CandidatesElectionRealizationService candidatesElectionRealizationService;
    private final VoteIdentificationCodeService voteIdentificationCodeService;

    public CandidacyVoteServiceImpl(CandidacyVoteRepository repository, TurnoutService turnoutService, CandidacyService candidacyService, CitizenService citizenService, CandidatesElectionRealizationService candidatesElectionRealizationService, VoteIdentificationCodeService voteIdentificationCodeService) {
        this.repository = repository;
        this.turnoutService = turnoutService;
        this.candidacyService = candidacyService;
        this.citizenService = citizenService;
        this.candidatesElectionRealizationService = candidatesElectionRealizationService;
        this.voteIdentificationCodeService = voteIdentificationCodeService;
    }

    public List<CandidacyVote> findAll() {
        return repository.findAll();
    }
    public CandidacyVote findById(Long id){

        return repository.findById(id).orElseThrow(() -> new RuntimeException("no candidacy vote found"));
    }
    public CandidacyVote update(Long voteId, Long candidacyId){
//        CandidacyVote cand = (CandidacyVote) voteService.findById(voteId);
//        cand.setCandidacy(candidacyService.findById(candidacyId));
//        return repository.save(cand);
        return null;
    }

    @Override
    @Transactional
    public void voteForCandidate(Long citizenId, Long realizationId, UUID voteId, Long id){
        Citizen citizen = citizenService.findById(citizenId);
        PollingStation pollingStation = citizen.getAddress().getPollingStation();
        VoteIdentificationCode voteIdentificationCode = voteIdentificationCodeService.findById(voteId);

//        turnoutService.update(null, LocalDateTime.now(), citizenId, realizationId);

        Vote vote = new Vote();
        vote.setVoteTimestamp(LocalDateTime.now());
        vote.setPollingStation(pollingStation);
        vote.setVoteIdentificationCode(voteIdentificationCode);
//        repository.voteForCandidate(voteId, id);
    }

    public CandidacyVote delete(Long id){
        CandidacyVote cv = findById(id);
        repository.delete(cv);
        return cv;
    }
}
