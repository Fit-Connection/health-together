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

    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestParam Long userId, @RequestParam Long friendId) {
        friendService.sendFriendRequest(userId, friendId);
        return ResponseEntity.ok("Friend request sent");
    }

    @PostMapping("/respond")
    public ResponseEntity<String> respondToFriendRequest(
            @RequestParam Long userId,
            @RequestParam Long friendId,
            @RequestParam String status) {
        friendService.respondToFriendRequest(userId, friendId, status);
        return ResponseEntity.ok("Friend request updated");
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Friend>> getFriendRequests(@RequestParam Long userId) {
        return ResponseEntity.ok(friendService.getFriendRequests(userId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Friend>> getFriends(@RequestParam Long userId) {
        return ResponseEntity.ok(friendService.getFriends(userId));
    }
}
