package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Room;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edit/books")
public class BookRestController  extends CommonRestController<Book, BookDTO> {
    public BookRestController(BookService service) {
        this.service = service;
        this.page = "books";
    }
    @GetMapping("/getBooks")
    public List<Book> getTopPage() {
        return ((BookService) service ).getTop25ByIdIsNotNullOrderByPrintTimeDescPopularityDesc().orElseThrow(NotFoundException::new);
    }

}
