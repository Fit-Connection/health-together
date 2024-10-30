package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private Long paymentId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String status;
}
