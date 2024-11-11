package com.ssafy.health.emailverify;

import com.ssafy.health.dto.request.emailcheck.EmailCheckRequest;
import com.ssafy.health.dto.request.emailcheck.EmailCheckResponse;
import com.ssafy.health.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080") // 필요에 따라 포트 번호 조정
@RequestMapping("/user/api/email")
public class EmailController {

    @Autowired
    private final EmailService emailService;
    @Autowired
    private UserService userService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> mailSend(@RequestBody EmailDto emailDto) throws MessagingException {
        System.out.println(emailDto.getMail());
        log.info("EmailController.mailSend()");
        emailService.sendEmail(emailDto.getMail());
        System.out.println("send");

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "인증코드가 발송되었습니다.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@RequestBody EmailDto emailDto) {
        log.info("EmailController.verify()");
        boolean isVerify = emailService.verifyEmailCode(emailDto.getMail(), emailDto.getVerifyCode());

        Map<String, String> response = new HashMap<>();
        if (isVerify) {
            response.put("status", "success");
            response.put("message", "인증이 완료되었습니다.");
        } else {
            response.put("status", "failure");
            response.put("message", "인증 실패하셨습니다.");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/check")
    public EmailCheckResponse checkEmail(@RequestBody EmailCheckRequest request) {
        boolean exists = userService.isEmailRegistered(request.getEmail()); // static 메서드로 호출하지 않음
        return new EmailCheckResponse(exists);
    }


}
