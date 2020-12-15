package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.validate_data.Exist;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book update(@Validated(Exist.class) @RequestBody BookDTO dto, @RequestParam(name = "id") Integer id) {
        if (id != null && id > 0) {
            find(id);
        }
        return ((BookService) service).updateBook(dto.fromDTO());
    }

    @PostMapping("/catalog")
    public List<Book> filterBook(@RequestBody Object[] criteria) {
        if (criteria != null) {
            Map<Genre, Integer> genreCriteria = new HashMap<>();
            Map<BookStatus, Integer> bookStatusCriteria = new HashMap<>();
            ArrayList<Map<String, String>> list = new ArrayList<>();
            ArrayList<Map<String, String>> arrayList = new ArrayList<>();
            if (criteria[0] != null) {
                list = (ArrayList<Map<String, String>>) criteria[0];
                for (Map<String, String> genreIntegerMap : list) {
                    genreCriteria.put(Genre.valueOf(genreIntegerMap.get("key")), Integer.valueOf(genreIntegerMap.get("value")));
                }
            }
            if (criteria[1] != null) {
                arrayList = (ArrayList<Map<String, String>>) criteria[1];
                for (Map<String, String> stringStringMap : arrayList) {
                    bookStatusCriteria.put(BookStatus.valueOf(stringStringMap.get("key")), Integer.valueOf(stringStringMap.get("value")));
                }
            }
//            Map<BookStatus,Integer> bookStatusCriteria = ( Map<BookStatus,Integer>)criteria[1];
            return ((BookService) service).filterByBookStatusAndGenre(bookStatusCriteria, genreCriteria);
        }
        return new LinkedList<>();
    }

}
