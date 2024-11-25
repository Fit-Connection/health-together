package com.ssafy.health.dto.response;

import java.time.LocalDateTime;

public record TeamMemberResponse(
        Long teamMemberId,
        Long teamId,
        Long userId,
        LocalDateTime joinedAt,
        String status
) {
}
