package info.jemsit.profile_service.service.impl;

import info.jemsit.common.dto.message.RabbitMQMessage;
import info.jemsit.common.dto.message.UserAvatarUpdated;
import info.jemsit.common.dto.request.auth.ProfileRequestDTO;
import info.jemsit.common.dto.response.auth.ProfileResponseDTO;
import info.jemsit.common.exceptions.UserException;
import info.jemsit.profile_service.data.dao.ProfileDAO;
import info.jemsit.profile_service.mapper.ProfileMapper;
import info.jemsit.profile_service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static info.jemsit.common.data.constants.RabbitMQConstants.MEDIA_QUEUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileDAO profileDAO;
    private final ProfileMapper profileMapper;
    @Value("${app.media.base-url}")
    private String mediaBaseURL;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public ProfileResponseDTO createProfile(ProfileRequestDTO request) {
        var profileEntity = profileMapper.toEntity(request);
        profileEntity.setProfileId(generateUniqueProfileCode());
        return profileMapper.toDTO(profileDAO.save(profileEntity), mediaBaseURL);
    }

    @Override
    public ProfileResponseDTO getById(Long id) {
        return profileMapper.toDTO(profileDAO.getById(id), mediaBaseURL);
    }

    @Override
    public ProfileResponseDTO updateProfile(Long id, ProfileRequestDTO request) {
        var profile = profileDAO.getById(id);
        if (request.phoneNumber() != null) {
            profile.setPhoneNumber(request.phoneNumber());
        }
        if (request.firstName() != null) {
            profile.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            profile.setLastName(request.lastName());
        }
        if (request.description() != null) {
            profile.setDescription(request.description());
        }
        return profileMapper.toDTO(profileDAO.update(profile), mediaBaseURL);
    }

    private String generateUniqueProfileCode() {
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            String code = String.valueOf(100_000 + RANDOM.nextInt(900_000));
            if (!profileDAO.existsByProfileCode(code)) {
                return code;
            }
        }
        throw new IllegalStateException("Could not generate unique profile code, consider expanding to 7 digits");
    }

    @Override
    @RabbitListener(queues = MEDIA_QUEUE, ackMode = "AUTO")
    public void handleRabbitMQMessage(RabbitMQMessage event) {
        log.info("Received message: {}", event.getClass().getSimpleName());
        switch (event) {
            case UserAvatarUpdated m -> updateProfileAvatar(Long.valueOf(m.getUserId()), m.getUserAvatarUrl());
            default -> log.warn("Received unknown message type: {}", event.getClass().getSimpleName());
        }
    }

    private void updateProfileAvatar(Long userId, String avatarUrl) {
        var userProfile = profileDAO.getByUserId(userId)
                .orElseThrow(() -> new UserException("User not found with id: " + userId));
        userProfile.setProfileImageUrl(avatarUrl);
        profileDAO.update(userProfile);
        log.info("Updated avatar for userProfile {}: {}", userId, avatarUrl);
    }
}
