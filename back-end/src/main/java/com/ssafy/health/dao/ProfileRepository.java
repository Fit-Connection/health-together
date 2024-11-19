package com.ssafy.health.dao;

import com.ssafy.health.domain.Profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileRepository {
    Profile findProfileByUserId(Long userId);
    void insertProfile(Profile profile);
    void updateProfile(Profile profile);
}

