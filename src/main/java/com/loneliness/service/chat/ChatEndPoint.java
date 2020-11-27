package com.loneliness.service.chat;


import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.RoomService;
import com.loneliness.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.Objects;
@Controller
@Component
@ServerEndpoint(value = "/{chat}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public final class ChatEndPoint {

    @Autowired
    private RoomService roomService;
    private Logger logger = LogManager.getLogger();
    private Room room;
    @OnOpen
    public void onOpen(@PathParam("chat") final Integer room, final Session session) {
        if (Objects.isNull(room) ) {
            throw new RegistrationFailedException("room name is required");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            user.setActivationCode(null);
            user.setActive(true);
            user.setActivationCode(null);
            user.setGoogleId(null);
                if (ChatSessionManager.register(session)) {
                logger.info("Chat opened for " + user.getUsername());
                this.room = roomService.findById(room).orElseThrow(NotFoundException::new);
                Message message = new Message();
                message.setAuthor(user);
                message.setMessage("add user");
                message.setDate(LocalDateTime.now());
                message.setRoom(this.room);
                ChatSessionManager.publish(message, session);
            } else {
                logger.error("Unable to register, username already exists");
                throw new RegistrationFailedException("Unable to register, username already exists");
            }
        }
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
        if (throwable.getClass() == RegistrationFailedException.class) {
            ChatSessionManager.close(session, CloseCodes.VIOLATED_POLICY, throwable.getMessage());
        }
    }

    @OnMessage
    public void onMessage(Message message, final Session session) {
        ChatSessionManager.publish(message, session);
    }

    @OnClose
    public void onClose(final Session session) {
        if (ChatSessionManager.remove(session)) {
            logger.info("Chat closed for %s\n" + session.getUserProperties().get(Constants.USER_NAME_KEY));
            Message message = new Message();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            message.setAuthor((User) auth.getPrincipal());
            message.setMessage("user left");
            message.setDate(LocalDateTime.now());
            message.setRoom(this.room);
            ChatSessionManager.publish(message, session);
        }
    }

    private static final class RegistrationFailedException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public RegistrationFailedException(final String message) {
            super(message);
        }
    }
}
