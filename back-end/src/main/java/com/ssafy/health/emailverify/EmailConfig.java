package com.ssafy.health.emailverify;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration  // 이 클래스가 스프링 설정 클래스임을 나타냅니다.
public class EmailConfig {

    @Value("${spring.mail.host}")  // 설정 파일에서 호스트 주소를 주입받습니다.
    private String host;

    @Value("${spring.mail.port}")  // 설정 파일에서 포트 번호를 주입받습니다.
    private int port;

    @Value("${spring.mail.username}")  // 설정 파일에서 이메일 계정을 주입받습니다.
    private String username;

    @Value("${spring.mail.password}")  // 설정 파일에서 비밀번호를 주입받습니다.
    private String password;

    // 인증 및 SSL/TLS 관련 속성 설정
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;

    @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
    private boolean sslEnable;

    // 연결 시간 제한 설정
    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;

    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);  // SMTP 서버 호스트 설정
        mailSender.setPort(port);  // 포트 번호 설정
        mailSender.setUsername(username);  // 사용자 이름(이메일 계정) 설정
        mailSender.setPassword(password);  // 비밀번호 설정
        mailSender.setDefaultEncoding("UTF-8");  // 문자 인코딩 설정
        mailSender.setJavaMailProperties(getMailProperties());  // SMTP 속성 설정

        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");  // 메일 전송 프로토콜 설정
        properties.setProperty("mail.smtp.auth", String.valueOf(auth));  // 인증 설정
        properties.setProperty("mail.smtp.starttls.enable", String.valueOf(starttlsEnable));  // TLS 사용 여부
        properties.setProperty("mail.smtp.ssl.enable", String.valueOf(sslEnable));  // SSL 사용 여부
        properties.setProperty("mail.debug", "true");  // 디버그 모드 활성화
        properties.setProperty("mail.smtp.ssl.trust", host);  // SSL을 사용하는 경우 호스트 신뢰
        properties.setProperty("mail.smtp.connectiontimeout", String.valueOf(connectionTimeout));  // 연결 시간 초과
        properties.setProperty("mail.smtp.timeout", String.valueOf(timeout));  // 메일 전송 시간 초과
        properties.setProperty("mail.smtp.writetimeout", String.valueOf(writeTimeout));  // 메일 쓰기 시간 초과

        return properties;
    }
}

