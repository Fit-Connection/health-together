package com.ssafy.health.service;

import com.ssafy.health.dto.request.LikeRequest;
import com.ssafy.health.dto.response.LikeResponse;

import java.util.Optional;

public interface LikeService {
    void toggleLike(LikeRequest likeRequest); // 좋아요 추가/삭제 토글

    int countLikesByTeamId(Long teamId); // 팀의 좋아요 수 조회

    Optional<LikeResponse> findLikeByUserAndTeam(Long userId, Long teamId); // 특정 좋아요 정보 조회
}
