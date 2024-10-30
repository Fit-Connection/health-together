package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {
    private Long facilityId;
    private String name;
    private String location;
    private String type;
    private Boolean availability;
}
