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
    private Boolean isActive; // 활성 상태 추가
}
