package com.ssafy.health.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private Long id; // 메시지 ID
    private String sender; // 메시지 보낸 사람
    private String content; // 메시지 내용
    private String roomId; // 채팅방 ID
    private LocalDateTime timestamp; // 메시지 전송 시간
}
