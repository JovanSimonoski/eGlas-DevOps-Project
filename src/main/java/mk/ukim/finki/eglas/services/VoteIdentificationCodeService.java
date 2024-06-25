package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.VoteIdentificationCode;

import java.time.LocalDateTime;
import java.util.UUID;

public interface VoteIdentificationCodeService {
    public void generateCodes(int n, LocalDateTime validUntil);
    public VoteIdentificationCode findRandomIdentificationCode();
    public VoteIdentificationCode findById(UUID id);
}
