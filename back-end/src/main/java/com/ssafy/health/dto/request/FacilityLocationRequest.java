package com.ssafy.health.dto.request;


public record FacilityLocationRequest(
        Long facilityId,
        Double latitude,
        Double longitude,
        Boolean status
) {
}
