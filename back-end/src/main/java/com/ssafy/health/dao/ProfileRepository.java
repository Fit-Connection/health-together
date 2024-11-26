package com.ssafy.health.dao;

import com.ssafy.health.domain.Profile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileRepository {
    Profile findProfileByUserId(Long userId);
    void insertProfile(Profile profile);
    void updateProfile(Profile profile);
    List<Profile> findProfileByUsername(String name);
}

