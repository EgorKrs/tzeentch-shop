package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/edit/books")
public class BookRestController  extends CommonRestController<Book, BookDTO> {
    public BookRestController(BookService service) {
        this.service = service;
        this.page = "books";
    }
}
