package com.ssafy.health.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamRequest {
    private Long teamId;
    private Long writerId;
    private String teamName;
    private String sportType;
    private String description;
    private Integer maxMembers;
    private String location;
    private LocalDateTime meetingDate;
    private String mapImageUrl;
}
