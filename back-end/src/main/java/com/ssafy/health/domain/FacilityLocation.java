package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityLocation {
    private Long facilityId;       // 시설 ID (외래 키)
    private Double latitude;   // 위도
    private Double longitude;  // 경도
    private Boolean status;        // 유효 상태
}
