package com.ssafy.health.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamRequest {
    private Long teamId;
    private Long userId;
    private String teamName; // 새 필드 추가
    private String sportType;
    private String description;
    private Integer maxMembers;
    private String location; // 새 필드 추가
    private LocalDateTime meetingDate; // 새 필드 추가
    private String mapImageUrl; // 새 필드 추가
}
