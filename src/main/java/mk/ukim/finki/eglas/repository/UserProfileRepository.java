package mk.ukim.finki.eglas.repository;

import mk.ukim.finki.eglas.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserName(String username);
}
