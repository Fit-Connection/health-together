package com.ssafy.health.dto.response;

import lombok.Data;

@Data
public class ChallengeResponse {
    private Long userId;
    private int totalScore;
    private String medal;

    public ChallengeResponse(Long userId, int totalScore, String medal) {
        this.userId = userId;
        this.totalScore = totalScore;
        this.medal = medal;
    }
}
