package info.jemsit.profile_service.data.dao.impl;

import info.jemsit.profile_service.data.dao.ProfileDAO;
import info.jemsit.profile_service.data.model.UserProfile;
import info.jemsit.profile_service.data.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileDAOImpl implements ProfileDAO {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfile save(UserProfile profile) {
        log.info("Saving user profile for userId: {}", profile.getUserId());
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile getById(Long id) {
        log.info("Fetching user profile by id: {}", id);
        return userProfileRepository.findById(id).orElse(null);
    }

    @Override
    public UserProfile getByUserId(Long userId) {
        return null;
    }

    @Override
    public UserProfile getByProfileId(String profileId) {
        return null;
    }

    @Override
    public UserProfile update(UserProfile profile) {
        log.info("Updating user profile for id: {}", profile.getId());
        return userProfileRepository.save(profile);
    }

    @Override
    public boolean existsByProfileCode(String code) {
        log.info("Checking existence of profile code: {}", code);
        return userProfileRepository.existsByProfileId(code);
    }
}
