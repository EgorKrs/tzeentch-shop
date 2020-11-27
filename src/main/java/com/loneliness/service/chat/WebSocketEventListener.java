package com.loneliness.service.chat;


import com.loneliness.entity.MessageType;
import com.loneliness.entity.domain.Message;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.RoomService;
import com.loneliness.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private CreateMessage messageBuilder;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection.");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Integer userId = (Integer) headerAccessor.getSessionAttributes().get("userId");
        Integer roomId = (Integer) headerAccessor.getSessionAttributes().get("room_id");
        if (userId != null) {
            logger.info("User Disconnected: " + userId);
            Message chatMessage = messageBuilder.create(userId,roomId);
            messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
        }
    }
}
