
package com.ssafy.health.controller;

import com.ssafy.health.domain.User;
import com.ssafy.health.dto.request.ChangePasswordRequest;
import com.ssafy.health.dto.request.ProfileDto;
import com.ssafy.health.dto.request.UserDto;
import com.ssafy.health.emailverify.EmailService;
import com.ssafy.health.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api")
@CrossOrigin(origins = "http://localhost:8080")  // Vue 개발 서버 포트로 설정
public class UserController {

    private final UserService userService;

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
        try {
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
            // 이메일 중복 확인
        } catch (DuplicateKeyException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        User existUser = userService.getUserByEmail(userDto.getEmail());

        if (userDto.getEmail().equals(existUser.getEmail()) && userDto.getPassword().equals(existUser.getPassword())) {
            // 로컬스토리지에 저장 => 임시 처리(프로필 설정 확인용) //
            Map<String, Object> response = new HashMap<>();
            response.put("userId", existUser.getUserId());
            response.put("email", existUser.getEmail());
            response.put("username", existUser.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
            ///////////////////////////////////////////////
            // 기존 로직
//            return new ResponseEntity<>(userDto, HttpStatus.OK);
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

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        userService.updatePasswordByEmail(request.getEmail(), request.getPassword());
    }

    @GetMapping("/search/{email}")
    public ResponseEntity<?> searchUser(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
