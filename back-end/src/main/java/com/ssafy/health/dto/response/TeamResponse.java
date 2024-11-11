package com.ssafy.health.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamResponse {
    private Long teamId;
    private Long userId;
    private String teamName; // 새 필드 추가
    private String sportType;
    private String description;
    private Integer maxMembers;
    private Integer currentMembers;
    private String location; // 새 필드 추가
    private LocalDateTime meetingDate; // 새 필드 추가
    private String mapImageUrl; // 새 필드 추가
    private LocalDateTime createdAt;
}
