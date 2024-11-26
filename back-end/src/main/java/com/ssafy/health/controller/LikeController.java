package com.ssafy.health.controller;

import com.ssafy.health.dto.request.LikeRequest;
import com.ssafy.health.dto.response.LikeResponse;
import com.ssafy.health.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * 좋아요 추가/삭제 토글
     */
    @PostMapping
    public ResponseEntity<Void> toggleLike(@RequestBody LikeRequest likeRequest) {
        likeService.toggleLike(likeRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 팀의 좋아요 수 조회
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> countLikes(@RequestParam Long teamId) {
        int count = likeService.countLikesByTeamId(teamId);
        return ResponseEntity.ok(count);
    }

    /**
     * 특정 사용자의 특정 팀에 대한 좋아요 정보 조회
     */
    @GetMapping("/find")
    public ResponseEntity<LikeResponse> findLike(
            @RequestParam Long userId,
            @RequestParam Long teamId) {
        Optional<LikeResponse> like = likeService.findLikeByUserAndTeam(userId, teamId);
        return ResponseEntity.of(like);
    }
}
