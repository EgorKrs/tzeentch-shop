package com.loneliness.controller;

import com.loneliness.dto.NewsDTO;
import com.loneliness.entity.domain.News;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.NewsService;
import com.loneliness.validate_data.Exist;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
    @GetMapping(value = "/getPage",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<News> getPage(@RequestParam(name = "page" ,required = false)Integer page ,
                              @RequestParam(name = "size" ,required = false) Integer size) throws IOException {
        if (page!=null) {
            if (size == null) {
                size = 25;
            }
            return ((NewsService) service).getNodes(page, size).orElse(new LinkedList<>());
        } else
            return service.findAll();

        //return service.save(dto.fromDTO());
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public News update(@Validated(Exist.class) @RequestBody NewsDTO dto, @RequestParam(name = "id") Integer id) {
        if (id == 0) {
            return ((NewsService) service).update(dto.fromDTO());
        } else return service.save(dto.fromDTO());
    }

}