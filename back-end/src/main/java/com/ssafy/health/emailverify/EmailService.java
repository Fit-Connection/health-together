package com.ssafy.health.emailverify;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private static final String senderEmail = "beomjun9701@gmail.com"; // 발신자 이메일

    private String createCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // 이메일 본문 설정
    private String setContext(String code) {
        return "<html><body>" +
                "<h3>안녕하세요, 인증 코드입니다.</h3>" +
                "<p>아래의 코드를 입력하여 인증을 완료하세요:</p>" +
                "<h2>" + code + "</h2>" +
                "</body></html>";
    }

    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("안녕하세요. 인증번호입니다.");
        message.setFrom(senderEmail);
        message.setContent(setContext(authCode), "text/html; charset=utf-8");

        // Redis 에 해당 인증코드와 인증 시간 설정
        redisUtil.setDataExpire(email, authCode, 60 * 30L);

        return message;
    }

    // 인증코드 이메일 발송
    public void sendEmail(String toEmail) throws MessagingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }
        // 이메일 폼 생성
        MimeMessage emailForm = createEmailForm(toEmail);
        // 이메일 발송
        javaMailSender.send(emailForm);
    }

    // 코드 검증
    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        log.info("code found by email: " + codeFoundByEmail);
        if (codeFoundByEmail == null) {
            return false;
        }
        return codeFoundByEmail.equals(code);
    }
}