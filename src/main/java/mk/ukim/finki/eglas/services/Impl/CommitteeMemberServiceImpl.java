package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.CommitteeMember;
import mk.ukim.finki.eglas.model.UserProfile;
import mk.ukim.finki.eglas.repository.CommitteeMemberRepository;
import mk.ukim.finki.eglas.repository.UserProfileRepository;
import mk.ukim.finki.eglas.services.CitizenService;
import mk.ukim.finki.eglas.services.CommitteeMemberService;
import mk.ukim.finki.eglas.services.UtilService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommitteeMemberServiceImpl implements CommitteeMemberService {


    private final CommitteeMemberRepository committeeMemberRepository;
    private final CitizenService citizenService;
    private final UtilService utilService;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public CommitteeMemberServiceImpl(CommitteeMemberRepository committeeMemberRepository, CitizenService citizenService, UtilService utilService, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.committeeMemberRepository = committeeMemberRepository;
        this.citizenService = citizenService;
        this.utilService = utilService;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CommitteeMember findById(Long id) {
        return committeeMemberRepository.findById(id).orElseThrow(() -> new RuntimeException("No committee member found"));
    }

    @Override
    public List<CommitteeMember> findAll() {
        return committeeMemberRepository.findAll();
    }

    @Override
    public CommitteeMember update(Long id) {
        committeeMemberRepository.insertCommitteeMember(id);
        Citizen c = citizenService.findById(id);
        String userName = utilService.cyrillicToLatinTransliteration(c.getName()) + '.' + utilService.cyrillicToLatinTransliteration(c.getSurname());
        UserProfile userProfile = userProfileRepository.findByUserName(userName);
        if(userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserName(userName);
            userProfile.setPassword(passwordEncoder.encode(c.getIdNum()));
            userProfile.setIsCommittee(true);
            userProfile.setCitizen(c);
        }
        else {
            userProfile.setIsCommittee(false);
        }
        System.out.println(userProfileRepository.save(userProfile));
        return findById(id);
    }

    @Override
    public void delete(Long id) {
        committeeMemberRepository.delete(findById(id));
    }



}
