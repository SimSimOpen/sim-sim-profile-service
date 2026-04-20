package info.jemsit.profile_service.controller;

import info.jemsit.common.dto.request.auth.ProfileRequestDTO;
import info.jemsit.profile_service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("create")
    public ResponseEntity<?> createProfile(@RequestBody ProfileRequestDTO request) {
        return ResponseEntity.ok(profileService.createProfile(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") Long profileId, @RequestBody ProfileRequestDTO request) {
        return ResponseEntity.ok(profileService.updateProfile(profileId, request));
    }
}
