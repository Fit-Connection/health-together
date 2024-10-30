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
    private Long teamMemberId;
    private Long teamId;
    private Long userId;
    private LocalDateTime joinedAt;
    private String status;
}
