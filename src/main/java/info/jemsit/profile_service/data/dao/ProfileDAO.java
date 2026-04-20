package info.jemsit.profile_service.data.dao;

import info.jemsit.profile_service.data.model.UserProfile;

public interface ProfileDAO {
    UserProfile save(UserProfile profile);
    UserProfile getById(Long id);
    UserProfile getByUserId(Long userId);
    UserProfile getByProfileId(String profileId);
    UserProfile update(UserProfile profile);
    boolean existsByProfileCode(String code);
}
