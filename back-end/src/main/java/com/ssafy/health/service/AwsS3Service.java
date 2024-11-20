package com.ssafy.health.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.health.config.AwsS3Config;
import com.ssafy.health.domain.AwsS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AmazonS3 amazonS3;
    private final AwsS3Config awsS3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 파일 업로드
    public AwsS3 upload(MultipartFile multipartFile, String dirName) throws IOException {
        String key = dirName + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        // S3에 파일 업로드 (ACL 제거)
        amazonS3.putObject(new PutObjectRequest(bucket, key, multipartFile.getInputStream(), null));

        // 업로드된 파일의 URL 반환
//        String path = amazonS3.getUrl(bucket, key).toString();
        String path = awsS3Config.getClooudFrontUrl(key); // cloudfront

        return AwsS3.builder()
                .key(key)
                .path(path)
                .build();
    }


    // 파일 삭제
    public void remove(AwsS3 awsS3) {
        amazonS3.deleteObject(bucket, awsS3.getKey());
    }
}
