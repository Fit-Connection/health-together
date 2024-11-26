package com.ssafy.health.service;

import com.ssafy.health.dao.LikeRepository;
import com.ssafy.health.domain.Like;
import com.ssafy.health.dto.request.LikeRequest;
import com.ssafy.health.dto.response.LikeResponse;
import com.ssafy.health.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    @Transactional
    public void toggleLike(LikeRequest likeRequest) {
        boolean exists = likeRepository.existsLikeByUserAndTeam(likeRequest.userId(), likeRequest.teamId());

        if (exists) {
            // 좋아요 삭제
            likeRepository.deleteLike(likeRequest);
        } else {
            // 좋아요 추가
            Like like = Like.builder()
                    .userId(likeRequest.userId())
                    .teamId(likeRequest.teamId())
                    .facilityId(likeRequest.facilityId())
                    .build();
            likeRepository.insertLike(like);
        }
    }

    @Override
    public int countLikesByTeamId(Long teamId) {
        return likeRepository.countLikesByTeamId(teamId);
    }

    @Override
    public Optional<LikeResponse> findLikeByUserAndTeam(Long userId, Long teamId) {
        return likeRepository.findLikeByUserAndTeam(userId, teamId)
                .map(like -> new LikeResponse(
                        like.getLikeId(),
                        like.getUserId(),
                        like.getTeamId(),
                        like.getFacilityId(),
                        like.getCreatedAt().toString()
                ));
    }
}
