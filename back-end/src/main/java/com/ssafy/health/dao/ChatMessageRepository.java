package com.ssafy.health.dao;

import com.ssafy.health.dto.request.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageRepository {
    void insertMessage(ChatMessage message); // 메시지 저장
    List<ChatMessage> findMessagesByRoomId(String roomId); // 메시지 조회
}

