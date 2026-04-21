package info.jemsit.profile_service.data.repository;

import info.jemsit.profile_service.data.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByProfileId(String code);

    Optional<UserProfile> findByUserId(Long userId);
}