package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.dto.NewsDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.News;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.service.NewsService;
import com.loneliness.validate_data.New;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/edit/news")
public class NewsRestController extends CommonRestController<News, NewsDTO> {

    public NewsRestController(NewsService service) {
        this.service = service;
        this.page = "news";
    }
    @GetMapping(value = "/getNews",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<News> getNews() throws IOException {
        return ((NewsService)service).getAllByIdIsNotNullOrderByPrintTimeDesc().orElseThrow(NotFoundException::new);
        //return service.save(dto.fromDTO());
    }

}