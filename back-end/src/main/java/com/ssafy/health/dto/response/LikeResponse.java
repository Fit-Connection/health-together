package com.ssafy.health.dto.response;

public record LikeResponse(
        Long likeId,
        Long userId,
        Long teamId,
        Long facilityId,
        String createdAt
) {}
