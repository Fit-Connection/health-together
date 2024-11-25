package com.ssafy.health.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FacilityResponse(
        Long facilityId,
        String name,
        String location,
        String type,
        String contact,
        String operatingHours,
        Boolean availability,
        BigDecimal ratingScore,
        Integer reviewCount,
        Integer popularity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
