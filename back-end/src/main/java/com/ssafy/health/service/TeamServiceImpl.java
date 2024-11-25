package com.ssafy.health.service;

import com.ssafy.health.dao.TeamRepository;
import com.ssafy.health.dto.request.TeamRequest;
import com.ssafy.health.dto.response.TeamResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<TeamResponse> getAllTeams() {
        return teamRepository.getAllTeams().stream()
                .map(team -> new TeamResponse(
                        team.teamId(),
                        team.writerId(),
                        team.writerName(),       // 추가된 필드
                        team.teamName(),
                        team.sportType(),
                        team.description(),
                        team.maxMembers(),
                        team.currentMembers(),
                        team.facilityId(),
                        team.facilityName(),     // 추가된 필드
                        team.facilityType(),     // 추가된 필드
                        team.facilityContact(),  // 추가된 필드
                        team.facilityLatitude(), // 추가된 필드
                        team.facilityLongitude(),// 추가된 필드
                        team.meetingDate(),
                        team.fee(),
                        team.ageLimit(),
                        team.preparing(),
                        team.rule(),
                        team.invitations(),
                        team.createdAt(),
                        team.updatedAt(),
                        team.isActive()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void createTeam(TeamRequest teamRequest) {
        teamRepository.createTeam(teamRequest.toEntity());
    }

    @Override
    public TeamResponse getTeamById(Long id) {
        var team = teamRepository.getTeamById(id);
        return new TeamResponse(
                team.teamId(),
                team.writerId(),
                team.writerName(),       // 추가된 필드
                team.teamName(),
                team.sportType(),
                team.description(),
                team.maxMembers(),
                team.currentMembers(),
                team.facilityId(),
                team.facilityName(),     // 추가된 필드
                team.facilityType(),     // 추가된 필드
                team.facilityContact(),  // 추가된 필드
                team.facilityLatitude(), // 추가된 필드
                team.facilityLongitude(),// 추가된 필드
                team.meetingDate(),
                team.fee(),
                team.ageLimit(),
                team.preparing(),
                team.rule(),
                team.invitations(),
                team.createdAt(),
                team.updatedAt(),
                team.isActive()
        );
    }

    @Override
    public void updateTeam(Long id, TeamRequest teamRequest) {
        teamRepository.updateTeam(teamRequest.toEntity(id));
    }

    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteTeam(id);
    }
}
