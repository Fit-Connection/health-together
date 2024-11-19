package com.ssafy.health.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwsS3 {
    private String key;  // S3 버킷의 키
    private String path; // 업로드된 파일의 URL
}
