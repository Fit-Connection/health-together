package com.ssafy.health.controller;

import com.ssafy.health.domain.AwsS3;
import com.ssafy.health.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    // 파일 업로드 엔드포인트
    @PostMapping("/resource")
    public AwsS3 upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        System.out.println("파일 업로드");
        return awsS3Service.upload(multipartFile, "upload");
    }

    // 파일 삭제 엔드포인트
    @DeleteMapping("/resource")
    public void remove(@RequestBody AwsS3 awsS3) {
        System.out.println("파일 삭제");
        awsS3Service.remove(awsS3);
    }
}
