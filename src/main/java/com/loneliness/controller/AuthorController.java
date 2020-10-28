package com.loneliness.controller;

import com.loneliness.dto.AuthorDTO;
import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.service.AuthorService;
import com.loneliness.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/author")
public class AuthorController extends CommonController<Author, AuthorDTO> {

    public AuthorController(AuthorService service) {
        this.service = service;
        this.page = "author";
    }

}