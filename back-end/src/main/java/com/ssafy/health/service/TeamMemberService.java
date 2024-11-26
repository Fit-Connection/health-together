package com.ssafy.health.service;

import com.ssafy.health.dto.request.TeamMemberRequest;
import com.ssafy.health.dto.response.TeamMemberResponse;

import java.util.List;

public interface TeamMemberService {
    List<TeamMemberResponse> getTeamMembers(Long teamId);
    void addTeamMember(TeamMemberRequest request);
    void updateTeamMemberStatus(Long teamMemberId, String status);
    void removeTeamMember(Long teamMemberId);
}
