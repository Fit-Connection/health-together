package com.ssafy.health.dto.request;

import lombok.Data;

@Data
public class TeamRequest {
    private Long userId; // 팀 생성자의 ID
    private String sportType;
    private String description;
    private Integer maxMembers;
}