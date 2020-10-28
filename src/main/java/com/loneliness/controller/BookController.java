package com.loneliness.controller;


import com.loneliness.dto.BookDTO;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.TranslationStatus;
import com.loneliness.entity.domain.Book;
import com.loneliness.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("book")
public class BookController extends CommonController<Book, BookDTO> {

    public BookController(BookService bookService) {
        this.service = bookService;
        this.page = "book";
    }
    @Override
    public String getOneById(@RequestParam(name = "id" ) Integer id , Map<String,Object> model) {
        Book book = new Book();
        book.setName("Book name");
        book.setDescription("very interesting book");
        book.setAvailability(1);
        book.setPrice(new BigDecimal("20"));
        book.setTranslationStatus(TranslationStatus.FINISHED);
        book.setBookStatus(BookStatus.FINISHED);
        model.put("Book",book);
        return page;
    }

}
