package com.ssafy.health.dao;

import com.ssafy.health.domain.Like;
import com.ssafy.health.dto.request.LikeRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface LikeRepository {
    void insertLike(Like like); // 좋아요 추가

    void deleteLike(LikeRequest likeRequest); // 좋아요 삭제

    int countLikesByTeamId(Long teamId); // 팀의 좋아요 수 조회

    boolean existsLikeByUserAndTeam(Long userId, Long teamId); // 좋아요 여부 확인

    Optional<Like> findLikeByUserAndTeam(Long userId, Long teamId); // 특정 좋아요 조회
}
