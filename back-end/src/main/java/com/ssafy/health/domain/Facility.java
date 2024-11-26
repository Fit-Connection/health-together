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
public class Facility {
    private Long facilityId;        // 시설 ID
    private String name;            // 시설 이름
    private String location;        // 상세 주소
    private String type;            // 시설 유형
    private String contact;         // 연락처
    private String operatingHours;  // 운영 시간
    private Boolean availability;   // 사용 가능 여부
    private BigDecimal ratingScore; // 평점
    private Integer reviewCount;    // 리뷰 개수
    private Integer popularity;     // 예약 횟수
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 마지막 수정 시간
}
