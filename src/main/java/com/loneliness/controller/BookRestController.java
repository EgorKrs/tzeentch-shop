package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Book;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.validate_data.Exist;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edit/books")
public class BookRestController extends CommonRestController<Book, BookDTO> {
    public BookRestController(BookService service) {
        this.service = service;
        this.page = "books";
    }

    @GetMapping("/getBooks")
    public List<Book> getTopPage() {
        return ((BookService) service).getTop25ByIdIsNotNullOrderByPrintTimeDescPopularityDesc().orElseThrow(NotFoundException::new);
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book update(@Validated(Exist.class) @RequestBody BookDTO dto, @RequestParam(name = "id") Integer id) {
        if (id != null && id > 0) {
            find(id);
        }
        return ((BookService) service).updateBook(dto.fromDTO());
    }

}
