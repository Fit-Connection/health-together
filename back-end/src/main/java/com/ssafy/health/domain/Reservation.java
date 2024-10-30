package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    private Long reservationId;
    private Long facilityId;
    private Long userId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String paymentStatus;
    private LocalDateTime createdAt;
}
