
package com.ssafy.health.service;

import com.ssafy.health.dao.ProfileRepository;
import com.ssafy.health.dao.UserRepository;
import com.ssafy.health.domain.Profile;
import com.ssafy.health.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public User getUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    public void createUser(User user) {
        userRepository.insertUser(user);

        // 회원가입 시 프로필 초기 설정
        Profile defaultProfile = Profile.builder()
                .userId(user.getUserId())
                .name(null) // 기본값 설정
                .location(null)
                .sports(null)
                .profileImage(null)
                .build();
        profileRepository.insertProfile(defaultProfile);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }

    public User getUserByName(String username) {
        return userRepository.selectUserByName(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.selectUserByEmail(email);
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }

    public void updatePasswordByEmail(String email, String password) {
        userRepository.updatePasswordByEmail(email, password);
    }
}
