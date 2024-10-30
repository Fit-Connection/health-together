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
public class Review {
    private Long reviewId;
    private Long reviewerId;
    private Long reviewedUserId;
    private Integer scoreChange;
    private String comment;
    private LocalDateTime createdAt;
}
