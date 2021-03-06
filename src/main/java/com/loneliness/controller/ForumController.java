package com.loneliness.controller;

import com.loneliness.dto.RoomDTO;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import com.loneliness.service.MessageService;
import com.loneliness.service.RoomService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;

@Controller
@RequestMapping("/forum")
public class ForumController extends CommonController<Room, RoomDTO> {
    private MessageService messageService ;
    public ForumController(RoomService service,MessageService messageService) {
        this.service = service;
        this.page = "themePage";
        this.messageService = messageService;
    }

    @GetMapping("/get")
    public String getPage(@RequestParam(name = "room") String title, Map<String, Object> model, HttpServletRequest request) {
        Room room = ((RoomService) service).findByTitle(title);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = (User) auth.getPrincipal();

            user.setActivationCode(null);
            user.setActive(true);
            user.setActivationCode(null);
            user.setGoogleId(null);
            model.put("messages", messageService.findAllByRoomTitleOrderByDateDateDesc(title).orElse(new LinkedList<>()));
            model.put("user", user);
            model.put("room", room);
            model.put("isAdmin", true);
            return page;
        } catch (java.lang.ClassCastException ex) {
            return "redirect:" + request.getScheme() + "://localhost:9080/login";
        }
    }

    @Override
    @GetMapping("/create")
    public String createPage(@RequestParam(name = "name") String name, Map<String, Object> model)
            throws IOException {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            Room room = new Room();
            room.setId(0);
            room.setTitle("Enter Title");
            room.setMessage("Enter Message");
            room.setPrintTime(Timestamp.valueOf(LocalDateTime.now()));
            user.setActivationCode(null);
            user.setActive(true);
            user.setActivationCode(null);
            user.setGoogleId(null);
            model.put("user", user);
            room.setAuthor(user);
            model.put("room", room);
            model.put("isAdmin", true);
            return page + "_edit";
        } catch (java.lang.ClassCastException ex) {
            return "redirect:" + "http://localhost:9080/login";
        }
    }

}
