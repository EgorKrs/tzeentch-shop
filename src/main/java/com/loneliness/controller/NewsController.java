package com.loneliness.controller;

import com.loneliness.dto.NewsDTO;
import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Picture;
import com.loneliness.entity.domain.User;
import com.loneliness.service.NewsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;

@Controller
@RequestMapping("/news")
public class NewsController extends CommonController<News, NewsDTO> {

    public NewsController(NewsService service) {
        this.service = service;
        this.page = "news";
    }

    @Override
    @GetMapping("/create")
    public String createPage(@RequestParam(name = "name") String name, Map<String, Object> model)
            throws IOException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            News news = new News();
            Picture picture = new Picture();
            picture.setUrl("../images/no-image.png");
            picture.setName("Enter name");
            picture.setId(-1);
            news.setPicture(picture);
            user.setActivationCode(null);
            user.setActive(true);
            user.setActivationCode(null);
            user.setGoogleId(null);
            model.put("user", user);
            news.setId(0);
            news.setReviews(new HashSet<>());
            news.setMessage("enter message");
            news.setPrintTime(Timestamp.valueOf(LocalDateTime.now()));
            news.setTitle("Enter title");
            news.setAuthor(user);
            model.put("News", news);
            return page + "_edit";
        } catch (java.lang.ClassCastException ex) {
            return "redirect:" + "http://localhost:9080/login";
        }
    }


}