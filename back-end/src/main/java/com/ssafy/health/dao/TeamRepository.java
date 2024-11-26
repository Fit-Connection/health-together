package com.ssafy.health.dao;

import com.ssafy.health.domain.Team;
import com.ssafy.health.dto.response.TeamResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRepository {
    TeamResponse getLatestTeam();

    List<TeamResponse> getAllTeams();
    // 팀 생성 메서드: 생성된 teamId를 반환하기 위해 Team 객체가 업데이트됨
    void createTeam(Team team);
    Team selectLatestTeam();
    TeamResponse getTeamResponseById(Long id);
    Team getTeamById(Long id);
    void updateTeam(Team team);
    void deleteTeam(Long id);
}
