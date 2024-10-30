package com.ssafy.health.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private Long messageId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime sentAt;
}
