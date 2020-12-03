package com.loneliness.controller;

import com.loneliness.dto.MessageDTO;
import com.loneliness.dto.RoomDTO;
import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.Room;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.MessageService;
import com.loneliness.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;



@RestController
@RequestMapping("/edit/forum")
public class ForumRestController extends  CommonRestController<Room, RoomDTO> {
    public ForumRestController(RoomService service) {
        this.service = service;
        this.page = "forum";
    }
    @GetMapping("/getForums")
    public List<Room> getTopPage() {
        return ((RoomService) service ).getTop4ByIdIsNotNullOrderByPrintTimeDesc().orElseThrow(NotFoundException::new);
    }
}