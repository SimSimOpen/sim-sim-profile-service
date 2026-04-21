package info.jemsit.profile_service.service;

import info.jemsit.common.dto.message.RabbitMQMessage;
import info.jemsit.common.dto.request.auth.ProfileRequestDTO;
import info.jemsit.common.dto.response.auth.ProfileResponseDTO;

public interface ProfileService {
    ProfileResponseDTO createProfile(ProfileRequestDTO request);

    ProfileResponseDTO getById(Long id);

    ProfileResponseDTO updateProfile(Long id, ProfileRequestDTO request);

    void handleRabbitMQMessage(RabbitMQMessage event);

}
