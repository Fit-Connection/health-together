package com.ssafy.health.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamResponse {
    private Long teamId;
    private Long userId;
    private String sportType;
    private String description;
    private Integer maxMembers;
    private Integer currentMembers;
    private LocalDateTime createdAt;
}