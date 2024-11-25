package com.ssafy.health.service;

import com.ssafy.health.dto.request.TeamRequest;
import com.ssafy.health.dto.response.TeamResponse;

import java.util.List;

public interface TeamService {
    List<TeamResponse> getAllTeams();
    void createTeam(TeamRequest teamRequest);
    TeamResponse getTeamById(Long id);
    void updateTeam(Long id, TeamRequest teamRequest);
    void deleteTeam(Long id);
}
