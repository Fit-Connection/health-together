
package com.ssafy.health.controller;

import com.ssafy.health.domain.User;
import com.ssafy.health.dto.request.UserDto;
import com.ssafy.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/api")
@CrossOrigin(origins = "http://localhost:8080")  // Vue 개발 서버 포트로 설정
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        User existUser = userService.getUserByName(userDto.getUsername());

        if (userDto.getUsername().equals(existUser.getUsername()) && userDto.getPassword().equals(existUser.getPassword())) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
