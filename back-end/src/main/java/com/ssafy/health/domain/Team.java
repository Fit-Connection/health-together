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
    private String teamName;
    private String sportType;
    private String description;
    private Integer maxMembers;
    private Integer currentMembers;
    private String location;
    private LocalDateTime meetingDate;
    private String mapImageUrl; // 새 필드 추가
    private LocalDateTime createdAt;
}
