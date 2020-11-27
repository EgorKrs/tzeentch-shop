package com.loneliness.controller;

import com.loneliness.dto.MessageDTO;
import com.loneliness.dto.NewsDTO;
import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import com.loneliness.service.MessageService;
import com.loneliness.service.NewsService;
import com.loneliness.service.RoomService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edit/message")
public class MessageRestController extends  CommonRestController<Message, MessageDTO> {
    public MessageRestController(MessageService service) {
        this.service = service;
        this.page = "message";
    }
    @GetMapping("/getByRoom")
    public List<Message> getPage(@RequestParam(name = "room") String title) {
        return ((MessageService) service ).findAllByRoomTitleOrderByDateDateDesc(title).orElse(new LinkedList<>());
    }
}
