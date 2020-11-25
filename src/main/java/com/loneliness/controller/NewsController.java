package com.loneliness.controller;

import com.loneliness.dto.AuthorDTO;
import com.loneliness.dto.NewsDTO;
import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.News;
import com.loneliness.service.AuthorService;
import com.loneliness.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/news")
public class NewsController extends CommonController<News, NewsDTO> {

    public NewsController(NewsService service) {
        this.service = service;
        this.page = "news";
    }

}