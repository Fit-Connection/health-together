package com.ssafy.health.service;

import com.ssafy.health.dao.ChatMessageRepository;
import com.ssafy.health.dto.request.ChatMessage;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void saveMessage(ChatMessage message) {
        chatMessageRepository.insertMessage(message); // 변경된 메서드명 반영
    }

    public List<ChatMessage> getMessagesByRoomId(String roomId) {
        return chatMessageRepository.findMessagesByRoomId(roomId); // 변경된 메서드명 반영
    }
}
