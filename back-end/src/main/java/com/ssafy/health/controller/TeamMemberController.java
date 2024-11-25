package com.ssafy.health.controller;

import com.ssafy.health.dto.request.TeamMemberRequest;
import com.ssafy.health.dto.response.TeamMemberResponse;
import com.ssafy.health.service.TeamMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team-members")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<List<TeamMemberResponse>> getTeamMembers(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamMemberService.getTeamMembers(teamId));
    }

    @PostMapping
    public ResponseEntity<Void> addTeamMember(@RequestBody TeamMemberRequest request) {
        teamMemberService.addTeamMember(request);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{teamMemberId}/status")
    public ResponseEntity<Void> updateTeamMemberStatus(@PathVariable Long teamMemberId, @RequestParam String status) {
        teamMemberService.updateTeamMemberStatus(teamMemberId, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamMemberId}")
    public ResponseEntity<Void> removeTeamMember(@PathVariable Long teamMemberId) {
        teamMemberService.removeTeamMember(teamMemberId);
        return ResponseEntity.noContent().build();
    }
}
