package com.ssafy.health.controller;

import com.ssafy.health.dto.response.ChallengeResponse;
import com.ssafy.health.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getChallengeInfo(@PathVariable Long userId) {
        int size = challengeService.challengeList(userId).size();
        int totalScore = 0;
        for(int i = 0; i < size; i++) {
            totalScore+=challengeService.challengeList(userId).get(i);
        }
        String medal = challengeService.calculateMedal(totalScore);
        return ResponseEntity.ok(new ChallengeResponse(userId, totalScore, medal));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getChallengeStatus(@PathVariable Long userId) {
        Map<String, Boolean> challengeStatus = challengeService.getChallengeStatus(userId);
        return ResponseEntity.ok(challengeStatus);
    }
}
