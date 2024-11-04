package com.ssafy.health.emailverify;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="email")
public  class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이메일 주소
    @Column(name = "email", nullable = false)
    private String email;

    // 이메일 인증 여부
    @Column(name = "email_status", nullable = false)
    private boolean emailStatus;

    @Builder
    public Email(String email, boolean emailStatus) {
        this.email = email;
        this.emailStatus = emailStatus;
    }
}
