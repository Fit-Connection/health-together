package com.ssafy.health.dao;

import com.ssafy.health.domain.TeamMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMemberRepository {
    List<TeamMember> getTeamMembers(Long teamId);
    void addTeamMember(TeamMember teamMember);
    void updateTeamMemberStatus(TeamMember teamMember);
    void removeTeamMember(Long teamMemberId);
}
