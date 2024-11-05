package com.ssafy.health.controller;

import com.ssafy.health.dto.request.TeamRequest;
import com.ssafy.health.dto.response.TeamResponse;
import com.ssafy.health.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:8080")  // Vue 개발 서버 포트로 설정
public class TeamController {
    private final TeamService teamService;

    // 팀 목록 조회
    @GetMapping
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        List<TeamResponse> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    // 팀 생성
    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody TeamRequest teamRequest) {
        TeamResponse createdTeam = teamService.createTeam(teamRequest);
        return ResponseEntity.ok(createdTeam);
    }

    // 특정 팀 조회
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        TeamResponse team = teamService.getTeamById(id);
        if (team != null) {
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 팀 정보 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Long id, @RequestBody TeamRequest teamRequest) {
        TeamResponse updatedTeam = teamService.updateTeam(id, teamRequest);
        if (updatedTeam != null) {
            return ResponseEntity.ok(updatedTeam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 팀 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        boolean deleted = teamService.deleteTeam(id);
        if (deleted) {
            return ResponseEntity.ok("팀이 삭제되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
