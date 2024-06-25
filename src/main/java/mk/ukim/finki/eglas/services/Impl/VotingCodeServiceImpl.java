package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.model.VotingCode;
import mk.ukim.finki.eglas.repository.VotingCodeRepository;
import mk.ukim.finki.eglas.services.CitizenService;
import mk.ukim.finki.eglas.services.ElectionRealizationService;
import mk.ukim.finki.eglas.services.TurnoutService;
import mk.ukim.finki.eglas.services.VotingCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VotingCodeServiceImpl implements VotingCodeService {

    private final VotingCodeRepository votingCodeRepository;
    private final TurnoutService turnoutService;
    private final CitizenService citizenService;
    private final ElectionRealizationService electionRealizationService;

    public VotingCodeServiceImpl(VotingCodeRepository votingCodeRepository, TurnoutService turnoutService, CitizenService citizenService, ElectionRealizationService electionRealizationService) {
        this.votingCodeRepository = votingCodeRepository;
        this.turnoutService = turnoutService;
        this.citizenService = citizenService;
        this.electionRealizationService = electionRealizationService;
    }


    @Override
    public List<VotingCode> findAll() {
        return votingCodeRepository.findAll();
    }

    @Override
    public VotingCode findByCode(String code) {
        return votingCodeRepository.findByCode(code);
    }

    @Override
    public VotingCode generateCode(Long citizenId, Long realizationId) {
        Boolean hasVoted = turnoutService.hasCitizenVotedOnRealization(citizenId, realizationId);
        if(hasVoted)
        {
            return null;
        }
        try
        {
            return findByCitizenIdAndRealizationId(citizenId, realizationId);
        }
        catch (Exception e)
        {
            Citizen citizen = citizenService.findById(citizenId);
            ElectionRealization electionRealization = electionRealizationService.findById(realizationId);
            VotingCode votingCode = new VotingCode(citizen, electionRealization);
            return votingCodeRepository.save(votingCode);
        }

    }

    @Override
    public VotingCode delete(Long id) {
        return null;
    }

    @Override
    public VotingCode findByCitizenIdAndRealizationId(Long citizenId, Long realizationId) {
        return votingCodeRepository.findByCitizen_IdAndElectionRealization_Id(citizenId, realizationId)
                .orElseThrow(() -> new RuntimeException("Voting code for the citizen not found!"));
    }

    @Override
    public Citizen findCitizenByCode(UUID code) {
        String codeString = code.toString();
        return votingCodeRepository.findCitizenByCode(codeString)
                .orElseThrow(() -> new RuntimeException("Citizen with given code not found or code expired!"));
    }
}
