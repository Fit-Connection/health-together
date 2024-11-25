package com.ssafy.health.dao;

import com.ssafy.health.domain.Team;
import com.ssafy.health.dto.response.TeamResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRepository {
    TeamResponse getLatestTeam();

    List<TeamResponse> getAllTeams();
    void createTeam(Team team);
    Team selectLatestTeam();
    TeamResponse getTeamById(Long id);
    void updateTeam(Team team);
    void deleteTeam(Long id);
}
