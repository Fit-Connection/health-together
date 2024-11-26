package com.ssafy.health.dto.request;

public record FacilityRequest(
        String name,
        String location,
        String type,
        String contact,
        String operatingHours,
        Boolean availability
) {
}
