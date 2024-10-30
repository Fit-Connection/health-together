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
    private Long facilityId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean status;
}
