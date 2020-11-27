package com.loneliness.controller;

import com.loneliness.dto.MessageDTO;
import com.loneliness.dto.NewsDTO;
import com.loneliness.dto.RoomDTO;
import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import com.loneliness.service.MessageService;
import com.loneliness.service.NewsService;
import com.loneliness.service.RoomService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
            return page;
        }
        catch (java.lang.ClassCastException ex){
            return "redirect:" + request.getScheme() +"://localhost:9080/login";
        }
    }
}
