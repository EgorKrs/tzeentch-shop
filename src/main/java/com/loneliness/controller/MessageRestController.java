package com.loneliness.controller;

import com.loneliness.dto.MessageDTO;
import com.loneliness.entity.domain.Message;
import com.loneliness.service.MessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/edit/message")
public class MessageRestController extends  CommonRestController<Message, MessageDTO> {
    public MessageRestController(MessageService service) {
        this.service = service;
        this.page = "message";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getByRoom")
    public List<Message> getPage(@RequestParam(name = "room") String title) {
        return ((MessageService) service ).findAllByRoomTitleOrderByDateDateDesc(title).orElse(new LinkedList<>());
    }
}
