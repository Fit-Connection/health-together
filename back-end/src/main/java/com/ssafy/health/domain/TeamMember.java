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
public class TeamMember {
    private Long teamMemberId;     // 팀 멤버 ID
    private Long teamId;           // 팀 ID
    private Long userId;           // 사용자 ID
    private LocalDateTime joinedAt; // 가입 시간
    private String status;         // 상태 (ACTIVE, LEFT 등)
}
