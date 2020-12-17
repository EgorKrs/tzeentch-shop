package com.loneliness.controller;

import com.loneliness.dto.BookDTO;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.validate_data.Exist;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book update(@Validated(Exist.class) @RequestBody BookDTO dto, @RequestParam(name = "id") Integer id) {
        if (id != null && id > 0) {
            find(id);
        }
        return ((BookService) service).updateBook(dto.fromDTO());
    }

    @PostMapping("/catalog")
    public List<Book> filterBook(@RequestBody Object[] criteria) throws ParseException {
        if (criteria != null) {
            Map<Genre, Integer> genreCriteria = new HashMap<>();
            Map<BookStatus, Integer> bookStatusCriteria = new HashMap<>();
            ArrayList<Map<String, String>> list = new ArrayList<>();
            ArrayList<Map<String, String>> arrayList = new ArrayList<>();
            if (criteria[0] != null) {
                list = (ArrayList<Map<String, String>>) criteria[0];
                for (Map<String, String> genreIntegerMap : list) {
                    for (final Genre genre : Genre.values()) {
                        if (genre.getGenre().equals(genreIntegerMap.get("key"))) {
                            genreCriteria.put(genre, Integer.valueOf(genreIntegerMap.get("value")));
                        }
                    }

                }
            }
            if (criteria[1] != null) {
                arrayList = (ArrayList<Map<String, String>>) criteria[1];
                for (Map<String, String> stringStringMap : arrayList) {

                    bookStatusCriteria.put(BookStatus.valueOf(stringStringMap.get("key")), Integer.valueOf(stringStringMap.get("value")));
                }
            }
            boolean isRandom = false;
            if (criteria[2] != null) {
                isRandom = (Boolean) criteria[2];
            }
            String authorName = null;
            if (criteria[3] != null) {
                authorName = (String) criteria[3];
            }
            boolean isPopular = false;
            if (criteria[4] != null) {
                isPopular = (Boolean) criteria[4];
            }
            String[] dateLimit = null;
            if (criteria[5] != null) {
                dateLimit = new String[2];
                ArrayList<String> dateLimitList = new ArrayList<>();
                dateLimitList = (ArrayList<String>) criteria[5];
                dateLimit[0] = dateLimitList.get(0);
                dateLimit[1] = dateLimitList.get(1);
            }
            Integer[] ratingLimit = null;
            if (criteria[6] != null) {
                ratingLimit = new Integer[2];
                ArrayList<String> ratingLimitList = new ArrayList<>();
                ratingLimitList = (ArrayList<String>) criteria[6];
                ratingLimit[0] = Integer.parseInt(ratingLimitList.get(0));
                ratingLimit[1] = Integer.parseInt(ratingLimitList.get(1));
            }
            Integer userId = null;
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User user = (User) auth.getPrincipal();
                if (user.getId() != null) {
                    userId = user.getId();
                }
            } catch (ClassCastException ex) {
                userId = null;
            }
            boolean isLikePast = false;
            if (criteria[7] != null) {
                isLikePast = (Boolean) criteria[7];
            }

//            Map<BookStatus,Integer> bookStatusCriteria = ( Map<BookStatus,Integer>)criteria[1];
            return ((BookService) service).filterByInputParam(bookStatusCriteria, genreCriteria, userId, isRandom,
                    authorName, isPopular, dateLimit, ratingLimit, isLikePast);
        }
        return new LinkedList<>();
    }

}
