package info.jemsit.profile_service.data.repository;

import info.jemsit.profile_service.data.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByProfileId(String code);
}