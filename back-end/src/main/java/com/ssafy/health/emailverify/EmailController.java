package com.ssafy.health.emailverify;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/user/api/email")
public class EmailController {

    private final EmailService emailService;

//    @PostMapping("/send")
//    public String mailSend(@RequestBody EmailDto emailDto) throws MessagingException {
//        log.info("EmailController.mailSend()");
//        emailService.sendEmail(emailDto.getMail());
//        System.out.println("send");
//        return "인증코드가 발송되었습니다.";
//    }

    @PostMapping("/verify")
    public String verify(@RequestBody EmailDto emailDto) {
        log.info("EmailController.verify()");
        boolean isVerify = emailService.verifyEmailCode(emailDto.getMail(), emailDto.getVerifyCode());

        return isVerify ? "인증이 완료되었습니다." : "인증 실패하셨습니다.";
    }
}
