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
public class Team {
    private Long teamId;           // 팀 ID
    private Long writerId;         // 작성자 ID
    private String teamName;       // 팀 이름
    private String sportType;      // 스포츠 유형
    private String description;    // 설명
    private String teamImageUrl;   // 팀 이미지
    private Integer maxMembers;    // 최대 팀원 수
    private Integer currentMembers; // 현재 팀원 수
    private Long facilityId;       // 시설 ID (외래 키)
    private LocalDateTime meetingDate; // 모임 날짜
    private String fee;            // 비용
    private String ageLimit;       // 연령 제한
    private String preparing;      // 준비물
    private String rule;           // 팀 규칙
    private String invitations;    // 초대 메시지
    private Boolean isActive;      // 활성 상태
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 마지막 수정 시간
}
