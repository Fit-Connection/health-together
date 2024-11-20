package com.ssafy.health.service;

import com.ssafy.health.dao.TeamRepository;
import com.ssafy.health.dto.request.TeamRequest;
import com.ssafy.health.dto.response.TeamResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional
    public TeamResponse createTeam(TeamRequest teamRequest) {
        int rowsAffected = teamRepository.createTeam(teamRequest);
        if (rowsAffected > 0) {
            return teamRepository.selectLatestTeam(); // 새로 생성된 팀을 조회
        }
        return null;
    }

    public List<TeamResponse> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    public TeamResponse getTeamById(Long id) {
        return teamRepository.getTeamById(id);
    }

    @Transactional
    public TeamResponse updateTeam(Long id, TeamRequest teamRequest) {
        teamRequest.setTeamId(id); // 요청에 팀 ID 설정
        int rowsAffected = teamRepository.updateTeam(teamRequest);
        if (rowsAffected > 0) {
            return teamRepository.getTeamById(id); // 업데이트된 팀을 조회
        }
        return null;
    }

    @Transactional
    public boolean deleteTeam(Long id) {
        int rowsAffected = teamRepository.deleteTeam(id);
        return rowsAffected > 0;
    }

    public List<TeamResponse> getTeamsByUserId(Long id) {
        return teamRepository.getTeamsByUserId(id);
    }
}
