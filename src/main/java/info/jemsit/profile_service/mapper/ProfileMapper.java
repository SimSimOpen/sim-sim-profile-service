package info.jemsit.profile_service.mapper;

import info.jemsit.common.dto.request.auth.ProfileRequestDTO;
import info.jemsit.common.dto.response.auth.ProfileResponseDTO;
import info.jemsit.profile_service.data.model.UserProfile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    UserProfile toEntity(ProfileRequestDTO request);

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    ProfileResponseDTO toDTO(UserProfile userProfile);
}
