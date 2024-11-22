package com.ssafy.health.controller;

import com.ssafy.health.dto.request.ChatMessage;
import com.ssafy.health.service.ChatMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatMessageService chatMessageService, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageService.saveMessage(chatMessage); // 메시지 저장
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getRoomId(), chatMessage); // 메시지 전송
    }

    @MessageMapping("/chat.history")
    public void getChatHistory(String roomId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByRoomId(roomId);
        messagingTemplate.convertAndSend("/topic/" + roomId + "/history", messages);
    }
}
