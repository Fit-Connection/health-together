package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    private Long teamId;
    private Long userId;
    private String sportType;
    private String description;
    private Integer maxMembers;
    private Integer currentMembers;
    private LocalDateTime createdAt;
}
