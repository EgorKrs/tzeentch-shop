package com.loneliness.service.chat;

import com.loneliness.entity.domain.Message;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.RoomService;
import com.loneliness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public  class CreateMessage {
    private  UserService userService;
    private  RoomService roomService;
    CreateMessage(UserService userService , RoomService roomService){
        this.userService =userService;
        this.roomService = roomService;
    }

    public Message create(Integer userId, Integer roomId){
        Message chatMessage = new Message();
        chatMessage.setAuthor(userService.findById(userId).orElseThrow(NotFoundException::new));
        chatMessage.setDate(LocalDateTime.now());
        chatMessage.setMessage("User Disconnected: " + chatMessage.getAuthor().getUsername());
        chatMessage.setRoom(roomService.findById(roomId).orElseThrow(NotFoundException::new));
        return chatMessage;
    }
    public Message create(Integer userId, Integer roomId,String message){
        Message chatMessage = new Message();
        chatMessage.setAuthor(userService.findById(userId).orElseThrow(NotFoundException::new));
        chatMessage.setDate(LocalDateTime.now());
        chatMessage.setMessage(message);
        chatMessage.setRoom(roomService.findById(roomId).orElseThrow(NotFoundException::new));
        return chatMessage;
    }
}
