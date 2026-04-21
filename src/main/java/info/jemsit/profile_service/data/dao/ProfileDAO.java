package info.jemsit.profile_service.data.dao;

import info.jemsit.profile_service.data.model.UserProfile;

import java.util.Optional;

public interface ProfileDAO {
    UserProfile save(UserProfile profile);
    UserProfile getById(Long id);
    Optional<UserProfile> getByUserId(Long userId);
    UserProfile getByProfileId(String profileId);
    UserProfile update(UserProfile profile);
    boolean existsByProfileCode(String code);
}
