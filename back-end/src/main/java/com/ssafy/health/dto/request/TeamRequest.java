package com.ssafy.health.dto.request;

import java.time.LocalDateTime;

public record TeamRequest(
        Long writerId,
        String teamName,
        String sportType,
        String description,
        String teamImageUrl,
        Integer maxMembers,
        String facilityName,
        String facilityLocation,
        String facilityType,
        String facilityContact,
        Double latitude, // 위도
        Double longitude, // 경도
        LocalDateTime meetingDate,
        String fee,
        String ageLimit,
        String preparing,
        String rule,
        String invitations,
        Boolean isActive
) {}
