package mk.ukim.finki.eglas.services;
import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.Committee;
import mk.ukim.finki.eglas.model.ElectionRealization;
import mk.ukim.finki.eglas.model.dto.CitizenVote;

import java.util.List;

public interface CommitteeService {
    Committee findById(Long id);
    List<Committee> findAll();
    void delete(Long id);
    Committee update(Long id, Long pollingStationId, Long electionRealizationId, List<Long> membersId);
    void addMemberToCommittee(Long committeeId, Long committeeMemberId);
    List<CitizenVote> getCitizens(Long committeeId);
    ElectionRealization getElectionRealization(Long committeeId);
    boolean getSamePollingStation(Long committeeMemberId, Long citizenId);
    ElectionRealization findElectionRealizationByCitizen(Long citizenId);
}
