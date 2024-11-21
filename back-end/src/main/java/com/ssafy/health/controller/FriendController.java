package com.ssafy.health.controller;

import com.ssafy.health.domain.Friend;
import com.ssafy.health.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/request/{userId}/{friendId}")
    public ResponseEntity<String> sendFriendRequest(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        friendService.sendFriendRequest(userId, friendId);
        return ResponseEntity.ok("Friend request sent");
    }

    @PostMapping("/respond/{userId}/{friendId}/{status}")
    public ResponseEntity<String> respondToFriendRequest(
            @PathVariable Long userId,
            @PathVariable Long friendId,
            @PathVariable String status) {
        friendService.respondToFriendRequest(userId, friendId, status);
        return ResponseEntity.ok("Friend request updated");
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<List<Friend>> getFriendRequests(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(friendService.getFriendRequests(userId));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<Friend>> getFriends(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(friendService.getFriends(userId));
    }

    @PostMapping("/remove/{userId}/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        friendService.removeFriendShip(userId, friendId);
        return ResponseEntity.ok("Friendship removed");
    }
}
