package mk.ukim.finki.eglas.services.Impl;

import mk.ukim.finki.eglas.model.Citizen;
import mk.ukim.finki.eglas.model.UserProfile;
import mk.ukim.finki.eglas.repository.UserProfileRepository;
import mk.ukim.finki.eglas.services.CitizenService;
import mk.ukim.finki.eglas.services.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

// Jovan
@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final CitizenService citizenService;

    UserProfileServiceImpl(UserProfileRepository userProfileRepository, CitizenService citizenService){
        this.userProfileRepository = userProfileRepository;
        this.citizenService = citizenService;
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile findById(Long id) {
        return userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("UserProfile not found"));
    }

    @Override
    public UserProfile update(Long id, String userName, String password, String role, Long citizenId) {
        Citizen citizen = citizenService.findById(citizenId);
        UserProfile userProfile = new UserProfile();
        if(id != null)
        {
            userProfile = findById(id);
        }
        userProfile.setUserName(userName);
        userProfile.setPassword(password);
        userProfile.setRole(role);
        userProfile.setCitizen(citizen);
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile delete(Long id) {
        UserProfile userProfile = findById(id);
        userProfileRepository.delete(userProfile);
        return userProfile;
    }

    @Override
    public UserProfile loadUserByUsername(String username) {
        return userProfileRepository.findByUserName(username);
    }
}
