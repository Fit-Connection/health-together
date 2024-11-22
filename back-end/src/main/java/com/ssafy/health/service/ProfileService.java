package com.ssafy.health.service;

import com.ssafy.health.dao.ProfileRepository;
import com.ssafy.health.domain.Profile;
import com.ssafy.health.domain.AwsS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AwsS3Service awsS3Service;

    public Profile getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findProfileByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("Profile not found for userId: " + userId);
        }
        return profile;
    }

    @Transactional
    public void createOrUpdateProfile(Profile profile) {
        Profile existingProfile = profileRepository.findProfileByUserId(profile.getUserId());
        if (existingProfile != null) {
            profileRepository.updateProfile(profile);
        } else {
            profileRepository.insertProfile(profile);
        }
    }

    @Transactional
    public String updateProfileImage(Long userId, MultipartFile file) throws IOException {
        // AWS S3에 이미지 업로드
        AwsS3 awsS3 = awsS3Service.upload(file, "profile-images");
        String imageUrl = awsS3.getPath();

        // 프로필 데이터에 이미지 URL 저장
        Profile profile = profileRepository.findProfileByUserId(userId);
        if (profile == null) {
            // 프로필이 없으면 새로 생성
            profile = Profile.builder()
                    .userId(userId)
                    .profileImage(imageUrl)
                    .build();
            profileRepository.insertProfile(profile);
        } else {
            // 기존 프로필 업데이트
            profile.setProfileImage(imageUrl);
            profileRepository.updateProfile(profile);
        }

        return imageUrl; // 업로드된 이미지 URL 반환
    }

    public List<Profile> getProfileByUserName(String name) {
        return profileRepository.findProfileByUsername(name);
    }
}
