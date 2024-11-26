package com.ssafy.health.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChallengeRepository {

    boolean checkProfileCompletion(Long userId);

    int countMeetingsHosted(Long userId);

    int countMeetingsParticipated(Long userId);

    int countFriends(Long userId);
}