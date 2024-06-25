package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.VoteIdentificationCode;
import mk.ukim.finki.eglas.repository.VoteIdentificationCodeRepository;
import mk.ukim.finki.eglas.services.VoteIdentificationCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoteIdentificationCodeServiceImpl implements VoteIdentificationCodeService {

    private final VoteIdentificationCodeRepository voteIdentificationCodeRepository;

    public VoteIdentificationCodeServiceImpl(VoteIdentificationCodeRepository voteIdentificationCodeRepository) {
        this.voteIdentificationCodeRepository = voteIdentificationCodeRepository;
    }

    @Override
    public void generateCodes(int n, LocalDateTime validUntil) {
        for(int i = 0; i < n; i++)
        {
            VoteIdentificationCode voteIdentificationCode = new VoteIdentificationCode(validUntil);
            voteIdentificationCodeRepository.save(voteIdentificationCode);
        }
    }

    @Override
    public VoteIdentificationCode findRandomIdentificationCode() {
        return voteIdentificationCodeRepository.findRandomVoteIdentificationCode()
                .orElseThrow(() -> new RuntimeException("No voting identification code available!"));
    }

    @Override
    public VoteIdentificationCode findById(UUID id) {
        return voteIdentificationCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote Identification Code not found!"));
    }
}
