
package com.ssafy.health.service;

import com.ssafy.health.dao.UserRepository;
import com.ssafy.health.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User getUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    public void createUser(User user) {
        userRepository.insertUser(user);
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


}
