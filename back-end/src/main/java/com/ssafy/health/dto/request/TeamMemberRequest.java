package com.ssafy.health.dto.request;

import java.time.LocalDateTime;

public record TeamMemberRequest(
        Long teamId,
        Long userId,
        String status
) {
}
