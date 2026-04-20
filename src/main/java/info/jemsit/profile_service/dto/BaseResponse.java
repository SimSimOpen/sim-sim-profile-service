package info.jemsit.profile_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface BaseResponse {
    @JsonProperty("timestamp")
    default long timestamp() {
        return System.currentTimeMillis();
    }
}