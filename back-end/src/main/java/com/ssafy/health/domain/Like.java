package com.ssafy.health.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    private Long likeId;       // 좋아요 ID
    private Long userId;       // 사용자 ID
    private Long teamId;       // 팀 ID
    private Long facilityId;   // 시설 ID
    private LocalDateTime createdAt; // 좋아요 생성 시간
}
