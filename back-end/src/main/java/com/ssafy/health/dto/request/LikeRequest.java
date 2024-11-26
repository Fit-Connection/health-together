package com.ssafy.health.dto.request;

public record LikeRequest(
      Long userId,
      Long teamId,
      Long facilityId) {
}
