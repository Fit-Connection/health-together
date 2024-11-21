package com.ssafy.health.service;

import com.ssafy.health.dao.FriendRepository;
import com.ssafy.health.domain.Friend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    public void sendFriendRequest(Long userId, Long friendId) {
        friendRepository.insertFriendRequest(new Friend(userId, friendId, "PENDING"));
    }

    public void respondToFriendRequest(Long userId, Long friendId, String status) {
        if (!status.equals("ACCEPTED") && !status.equals("REJECTED")) {
            throw new IllegalArgumentException("Invalid status");
        }
        friendRepository.updateFriendStatus(Map.of("userId", friendId, "friendId", userId, "status", status));
    }

    public List<Friend> getFriendRequests(Long userId) {
        return friendRepository.getFriendRequests(userId);
    }

    public List<Friend> getFriends(Long userId) {
        return friendRepository.getFriendsList(userId);
    }

    public void removeFriendShip(Long userId, Long friendId) {
        friendRepository.removeFriendShip(userId, friendId);
    }
}

