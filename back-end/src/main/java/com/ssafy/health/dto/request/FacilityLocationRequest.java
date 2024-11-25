package com.ssafy.health.dto.request;

import java.math.BigDecimal;

public record FacilityLocationRequest(
        Long facilityId,
        BigDecimal latitude,
        BigDecimal longitude,
        Boolean status
) {
}
