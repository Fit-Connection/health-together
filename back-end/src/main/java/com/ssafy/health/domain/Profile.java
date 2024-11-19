package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    private Long id;
    private Long userId;           // users 테이블의 user_id를 참조
    private String name;           // 이름
    private String location;       // 위치
    private String sports;         // 선호 스포츠
    private String profileImage;   // 프로필 이미지 경로
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

