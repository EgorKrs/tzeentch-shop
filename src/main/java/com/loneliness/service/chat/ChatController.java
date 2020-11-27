package com.loneliness.service.chat;


import com.loneliness.entity.MessageType;
import com.loneliness.entity.domain.Message;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

import static java.lang.String.format;

@Controller
public class ChatController {



    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private CreateMessage messageBuilder;
    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload Message chatMessage) {
        Message fullMessage = messageBuilder.create(chatMessage.getAuthor().getId(),
                chatMessage.getRoom().getId(),chatMessage.getMessage());
        fullMessage.setId(messageService.saveAndReturnId(fullMessage));
        messagingTemplate.convertAndSend(format("/channel/%s", roomId), fullMessage);
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable Integer roomId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            Message leaveMessage = messageBuilder.create(chatMessage.getAuthor().getId(),roomId);
            messagingTemplate.convertAndSend(format("/channel/%s", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("userId", chatMessage.getAuthor().getId());
        chatMessage.setDate(LocalDateTime.now());
        messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
    }
}

