package mk.ukim.finki.eglas.services;

import mk.ukim.finki.eglas.model.UserProfile;

import java.util.List;

public interface UserProfileService {
    List<UserProfile> findAll();
    UserProfile findById(Long id);
    UserProfile update(Long id, String userName, String password, String role, Long citizenId);
    UserProfile delete(Long id);
    UserProfile loadUserByUsername(String username);
}
