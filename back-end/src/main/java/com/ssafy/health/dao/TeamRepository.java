package com.ssafy.health.dao;

import com.ssafy.health.dto.request.TeamRequest;
import com.ssafy.health.dto.response.TeamResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamRepository {

    int createTeam(TeamRequest teamRequest);

    List<TeamResponse> getAllTeams();

    TeamResponse getTeamById(@Param("id") Long id);

    TeamResponse selectLatestTeam(); // 새로 생성된 팀을 조회하는 메서드

    int updateTeam(TeamRequest teamRequest);

    int deleteTeam(@Param("id") Long id);

    List<TeamResponse> getTeamsByUserId(Long id);
}
