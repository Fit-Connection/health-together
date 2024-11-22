package com.ssafy.health.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamResponse {
    private Long teamId;
    private Long writerId;
    private String teamName;
    private String sportType;
    private String description;
    private Integer maxMembers;
    private Integer currentMembers;
    private String location;
    private LocalDateTime meetingDate;
    private String mapImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; // 마지막 수정 시간
}
