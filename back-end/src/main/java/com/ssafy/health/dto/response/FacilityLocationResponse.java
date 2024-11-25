package com.ssafy.health.dto.response;

import java.math.BigDecimal;

public record FacilityLocationResponse(
        Long facilityId,
        BigDecimal latitude,
        BigDecimal longitude,
        Boolean status
) {
}
