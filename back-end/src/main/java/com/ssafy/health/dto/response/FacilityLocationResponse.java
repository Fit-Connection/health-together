package com.ssafy.health.dto.response;

import java.math.BigDecimal;

public record FacilityLocationResponse(
        Long facilityId,
        Double latitude,
        Double longitude,
        Boolean status
) {
}
