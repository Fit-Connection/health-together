package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Character {
    private Long userId;
    private Integer characterLevel;
    private Integer exp;
    private LocalDateTime lastUpdated;
}
