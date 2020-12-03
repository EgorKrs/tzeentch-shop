package com.loneliness.controller;


import com.loneliness.dto.BookDTO;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.TranslationStatus;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.service.BookService;
import com.loneliness.util.json_parser.JsonParser;
import com.loneliness.util.search.SearchCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
    @GetMapping("/catalog")
    public String getAllPage(Map<String,Object> model,
                             @RequestParam( name = "genre",required = false) Genre genre,
                             @RequestParam( name = "bookStatus", required = false) BookStatus bookStatus) throws IOException {
        List<Book> nodes = new LinkedList<>();
        if (genre !=null || bookStatus!= null){
            final List<SearchCriteria> params = new ArrayList<>();
            if (genre!= null) {
                nodes = new LinkedList<>(((BookService) service).findAllByGenresContains(genre).orElse(new LinkedList<>()));

            }
            if(bookStatus!=null){
                params.add(SearchCriteria.builder().operation("=").key("bookStatus").value(bookStatus.name()).build());
                nodes.addAll(service.search(params, Book.class));
            }

        }
        else {
             nodes = service.findAll();

        }
        model.put("Books", JsonParser.mapToJson(nodes));
        return "All" + page ;
    }

}
