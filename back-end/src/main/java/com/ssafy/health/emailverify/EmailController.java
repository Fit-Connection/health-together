package com.ssafy.health.emailverify;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> mailSend(@RequestBody EmailDto emailDto) throws MessagingException {
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
}
