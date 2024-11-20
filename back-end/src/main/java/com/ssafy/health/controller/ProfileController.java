package com.ssafy.health.controller;

import com.ssafy.health.domain.Profile;
import com.ssafy.health.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 특정 사용자 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable Long userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    // 프로필 생성 또는 업데이트
    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createOrUpdateProfile(@PathVariable Long userId, @RequestBody Profile profile) {
        profile.setUserId(userId);
        profileService.createOrUpdateProfile(profile);
        return ResponseEntity.ok("프로필이 설정되었습니다.");
    }

    // 프로필 이미지 업로드 및 URL 저장
    @PostMapping("/{userId}/image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long userId, @RequestPart("file") MultipartFile file) {
        try {
            String imageUrl = profileService.updateProfileImage(userId, file);
            return ResponseEntity.ok("이미지가 업로드되었습니다: " + imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("이미지 업로드 실패: " + e.getMessage());
        }
    }
}
