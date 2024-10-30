package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {
    private Long challengeId;
    private Long userId;
    private String goal;
    private String reward;
    private String status;
}
