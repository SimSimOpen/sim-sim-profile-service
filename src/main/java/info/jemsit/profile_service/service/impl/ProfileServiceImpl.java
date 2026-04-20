package info.jemsit.profile_service.service.impl;

import info.jemsit.common.dto.request.auth.ProfileRequestDTO;
import info.jemsit.common.dto.response.auth.ProfileResponseDTO;
import info.jemsit.profile_service.data.dao.ProfileDAO;
import info.jemsit.profile_service.mapper.ProfileMapper;
import info.jemsit.profile_service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileDAO profileDAO;
    private final ProfileMapper profileMapper;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public ProfileResponseDTO createProfile(ProfileRequestDTO request) {
        var profileEntity = profileMapper.toEntity(request);
        profileEntity.setProfileId(generateUniqueProfileCode());
        return profileMapper.toDTO(profileDAO.save(profileEntity));
    }

    @Override
    public ProfileResponseDTO getById(Long id) {
        return profileMapper.toDTO(profileDAO.getById(id));
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
        return profileMapper.toDTO(profileDAO.update(profile));
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
}
