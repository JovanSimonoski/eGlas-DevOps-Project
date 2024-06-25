package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.VotingCode;

import java.util.List;
import java.util.UUID;

public interface VotingCodeService {
    List<VotingCode> findAll();
    VotingCode findByCode(String code);
    VotingCode generateCode(Long citizenId, Long realizationId);
    VotingCode delete(Long id);
    VotingCode findByCitizenIdAndRealizationId(Long citizenId, Long realizationId);
    Citizen findCitizenByCode(UUID code);
}
