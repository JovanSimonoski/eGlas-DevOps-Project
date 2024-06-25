package mk.ukim.finki.eglas.services.Impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eglas.model.*;
import mk.ukim.finki.eglas.model.dto.CitizenVote;
import mk.ukim.finki.eglas.repository.CitizenRepository;
import mk.ukim.finki.eglas.repository.CommitteeMemberRepository;
import mk.ukim.finki.eglas.repository.CommitteeRepository;
import mk.ukim.finki.eglas.services.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommitteeServiceImpl implements CommitteeService {

    private final CommitteeMemberService committeeMemberService;
    private final CommitteeRepository committeeRepository;
    private final AddressService addressService;
    private final ElectionRealizationService electionRealizationService;
    private final UtilService utilService;
    private final CitizenRepository citizenRepository;
    private final CommitteeMemberRepository committeeMemberRepository;

    public CommitteeServiceImpl(CommitteeMemberService committeeMemberService,
                                CommitteeRepository committeeRepository,
                                AddressService addressService,
                                ElectionRealizationService electionRealizationService, UtilService utilService, CitizenRepository citizenRepository, CommitteeMemberRepository committeeMemberRepository) {
        this.committeeMemberService = committeeMemberService;
        this.committeeRepository = committeeRepository;
        this.addressService = addressService;
        this.electionRealizationService = electionRealizationService;
        this.utilService = utilService;
        this.citizenRepository = citizenRepository;
        this.committeeMemberRepository = committeeMemberRepository;
    }

    @Override
    public Committee findById(Long id) {
        return committeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Committee not found"));
    }

    @Override
    public List<Committee> findAll() {
        return committeeRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        committeeRepository.delete(findById(id));
    }

    @Transactional
    @Override
    public Committee update(Long id, Long pollingStationId, Long electionRealizationId, List<Long> membersId) {
        Committee committee;
        if (id != null){
            committee = findById(id);
        } else {
            committee = new Committee();
        }

        committee.setPollingStation(addressService.findPollingStationById(pollingStationId));
        committee.setElectionRealization(electionRealizationService.    findById(electionRealizationId));
        committeeRepository.save(committee);
        membersId.forEach(memberId -> addMemberToCommittee(committee.getId(), memberId));
        return committeeRepository.save(committee);
    }

    @Override
    @Transactional
    public void addMemberToCommittee(Long committeeId, Long committeeMemberId) {
        Committee committee = findById(committeeId);
        committee.getMembers().add(committeeMemberService.findById(committeeMemberId));
        committeeRepository.save(committee);
    }

    @Override
    public List<CitizenVote> getCitizens(Long committeeId) {
        CommitteeMember committeeMember = committeeMemberRepository.findById(committeeId).orElseThrow(RuntimeException::new);
        Committee committee = committeeRepository.findCommitteeByMembersContainsAndElectionRealization_DateIsAfter(committeeMember, LocalDate.now().minusDays(3));
        PollingStation pollingStation = committee.getPollingStation();
        return citizenRepository.findAllByAddress_PollingStation(pollingStation.getId()).stream().filter(x -> x instanceof CitizenVote).toList();
    }

    @Override
    public ElectionRealization getElectionRealization(Long committeeId){
        Committee committee = findById(committeeId);
        return null;
    }

    @Override
    public ElectionRealization findElectionRealizationByCitizen(Long citizenId){
        CommitteeMember committeeMember = committeeMemberRepository.findById(citizenId).orElseThrow(RuntimeException::new);
        return committeeRepository.findCommitteeByMembersContainsAndElectionRealization_DateIsAfter(committeeMember, LocalDate.now().minusDays(3)).getElectionRealization();
    }

    @Override
    public boolean getSamePollingStation(Long committeeMemberId, Long citizenId){
        Committee committee = committeeRepository.findCommitteeByMembersContainsAndElectionRealization_DateIsAfter(committeeMemberService.findById(committeeMemberId), LocalDate.now().minusDays(3));
        return addressService.findPollingStationByCitizenId(citizenId).getId() == committee.getPollingStation().getId();
    }
}
