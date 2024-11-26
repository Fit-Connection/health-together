package com.ssafy.health.dto.response;

import java.time.LocalDateTime;
public record TeamResponse(
        Long teamId,
        Long writerId,
        String writerName,           // 추가
        String teamName,
        String sportType,
        String description,
        String teamImageUrl,
        Integer maxMembers,
        Integer currentMembers,
        Long facilityId,
        String facilityName,         // 추가
        String facilityLocation,         // 추가
        String facilityType,         // 추가
        String facilityContact,      // 추가
        Double facilityLatitude,     // 추가
        Double facilityLongitude,    // 추가
        LocalDateTime meetingDate,
        String fee,
        String ageLimit,
        String preparing,
        String rule,
        String invitations,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isActive
) {
}
