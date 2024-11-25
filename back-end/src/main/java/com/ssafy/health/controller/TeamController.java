package com.ssafy.health.controller;

import com.ssafy.health.dto.request.TeamRequest;
import com.ssafy.health.dto.response.TeamResponse;
import com.ssafy.health.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestBody TeamRequest teamRequest) {
        teamService.createTeam(teamRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTeam(@PathVariable Long id, @RequestBody TeamRequest teamRequest) {
        teamService.updateTeam(id, teamRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
