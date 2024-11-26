package com.ssafy.health.service;

import com.ssafy.health.dao.TeamMemberRepository;
import com.ssafy.health.domain.TeamMember;
import com.ssafy.health.dto.request.TeamMemberRequest;
import com.ssafy.health.dto.response.TeamMemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public List<TeamMemberResponse> getTeamMembers(Long teamId) {
        return teamMemberRepository.getTeamMembers(teamId).stream()
                .map(member -> new TeamMemberResponse(
                        member.getTeamMemberId(),
                        member.getTeamId(),
                        member.getUserId(),
                        member.getJoinedAt(),
                        member.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void addTeamMember(TeamMemberRequest request) {
        TeamMember teamMember = TeamMember.builder()
                .teamId(request.teamId())
                .userId(request.userId())
                .status(request.status())
                .build();
        teamMemberRepository.addTeamMember(teamMember);
    }

    @Override
    public void updateTeamMemberStatus(Long teamMemberId, String status) {
        TeamMember teamMember = TeamMember.builder()
                .teamMemberId(teamMemberId)
                .status(status)
                .build();
        teamMemberRepository.updateTeamMemberStatus(teamMember);
    }

    @Override
    public void removeTeamMember(Long teamMemberId) {
        teamMemberRepository.removeTeamMember(teamMemberId);
    }
}
