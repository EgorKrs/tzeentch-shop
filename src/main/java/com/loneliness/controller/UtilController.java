package com.loneliness.controller;

import com.loneliness.entity.BookStatus;
import com.loneliness.entity.Role;
import com.loneliness.entity.domain.Genre;
import com.loneliness.entity.domain.User;
import com.loneliness.util.json_parser.JsonParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UtilController {
    @GetMapping()
    public String getIndex(Map<String, Object> model) throws IOException {
        model.put("AllGenre", JsonParser.mapToJson(Genre.values()));
        model.put("AllBookStatus", JsonParser.mapToJson(BookStatus.values()));
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            if (user.getId() != null) {
                model.put("login", true);
                model.put("isAdmin", user.getRoles().contains(Role.ADMIN));
            }
        } catch (ClassCastException ex) {
            model.put("login", false);
            model.put("isAdmin", false);
        }
        return "index";
    }
}
