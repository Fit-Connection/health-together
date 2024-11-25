package com.ssafy.health.dto.request;

import com.ssafy.health.domain.Team;

import java.time.LocalDateTime;

public record TeamRequest(
        Long writerId,
        String teamName,
        String sportType,
        String description,
        Integer maxMembers,
        Long facilityId,
        LocalDateTime meetingDate,
        String fee,
        String ageLimit,
        String preparing,
        String rule,
        String invitations,
        Boolean isActive // 추가된 필드
) {
    public Team toEntity(Long teamId) {
        return Team.builder()
                .teamId(teamId)
                .writerId(this.writerId())
                .teamName(this.teamName())
                .sportType(this.sportType())
                .description(this.description())
                .maxMembers(this.maxMembers())
                .facilityId(this.facilityId())
                .meetingDate(this.meetingDate())
                .fee(this.fee())
                .ageLimit(this.ageLimit())
                .preparing(this.preparing())
                .rule(this.rule())
                .invitations(this.invitations())
                .isActive(this.isActive())
                .build();
    }

    public Team toEntity() {
        return Team.builder()
                .writerId(this.writerId())
                .teamName(this.teamName())
                .sportType(this.sportType())
                .description(this.description())
                .maxMembers(this.maxMembers())
                .facilityId(this.facilityId())
                .meetingDate(this.meetingDate())
                .fee(this.fee())
                .ageLimit(this.ageLimit())
                .preparing(this.preparing())
                .rule(this.rule())
                .invitations(this.invitations())
                .isActive(this.isActive())
                .build();
    }
}
