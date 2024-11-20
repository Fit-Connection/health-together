package com.ssafy.health.dao;

import com.ssafy.health.domain.Friend;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Mapper
public interface FriendRepository {
    void insertFriendRequest(Friend pending);

    void updateFriendStatus(Map<String,? extends Serializable> userId);

    List<Friend> getFriendRequests(Long userId);

    List<Friend> getFriendsList(Long userId);
}

