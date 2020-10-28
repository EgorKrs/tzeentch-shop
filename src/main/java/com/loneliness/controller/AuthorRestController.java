package com.loneliness.controller;

import com.loneliness.dto.AuthorDTO;
import com.loneliness.entity.domain.Author;
import com.loneliness.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/edit/author")
public class AuthorRestController extends CommonRestController<Author, AuthorDTO> {

    public AuthorRestController(AuthorService service) {
        this.service = service;
        this.page = "author";
    }

}
