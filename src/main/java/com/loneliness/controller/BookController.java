package com.loneliness.controller;


import com.loneliness.dto.BookDTO;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.entity.domain.User;
import com.loneliness.entity.domain.UserCreditDetails;
import com.loneliness.exception.NotEnoughMoneyException;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.service.UserCreditService;
import com.loneliness.service.UserService;
import com.loneliness.util.MediaTypeUtils;
import com.loneliness.util.json_parser.JsonParser;
import com.loneliness.util.search.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("book")
public class BookController extends CommonController<Book, BookDTO> {
    @Autowired
    private ServletContext servletContext;
    private UserCreditService userCreditService;
    private UserService userService;

    public BookController(BookService bookService, UserCreditService userCreditService, UserService userService) {
        this.service = bookService;
        this.page = "book";
        this.userCreditService = userCreditService;
        this.userService = userService;
    }

    @Override
    public String getOneById(@RequestParam(name = "id") Integer id, Map<String, Object> model) throws IOException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            boolean isFound = false;
            for (Book book : user.getBooks()) {
                if (book.getId().equals(id)) {
                    isFound = true;
                    break;
                }
            }
            model.put("Bought", isFound);
        } catch (ClassCastException ex) {
            model.put("Bought", false);
        }
        Optional<Book> book = service.findById(id);
        model.put("Book", book.orElse(new Book()));
        if (book.isPresent() && book.get().getRelatedBooks() != null) {
            model.put("RelatedBooks", JsonParser.mapToJson(book.get().getRelatedBooks()));
        } else model.put("RelatedBooks", JsonParser.mapToJson(new LinkedList<Book>()));
        if (book.isPresent() && book.get().getGenres() != null) {
            model.put("Genres", JsonParser.mapToJson(book.get().getGenres()));
        }
        return page;
    }

    @GetMapping("/catalog")
    public String getAllPage(Map<String, Object> model,
                             @RequestParam(name = "genre", required = false) Genre genre,
                             @RequestParam(name = "bookStatus", required = false) BookStatus bookStatus) throws IOException {
        List<Book> nodes = new LinkedList<>();
        if (genre != null || bookStatus != null) {
            final List<SearchCriteria> params = new ArrayList<>();
            if (genre != null) {
                nodes = new LinkedList<>(((BookService) service).findAllByGenresContains(genre).orElse(new LinkedList<>()));

            }
            if(bookStatus!=null){
                params.add(SearchCriteria.builder().operation("=").key("bookStatus").value(bookStatus.name()).build());
                nodes.addAll(service.search(params, Book.class));
            }

        } else {
            nodes = service.findAll();

        }
        model.put("Books", JsonParser.mapToJson(nodes));
        return "All" + page;
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile1(
            @RequestParam(value = "id") Integer id) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        UserCreditDetails creditDetails = userCreditService.findById(user.getId()).orElseThrow(NotFoundException::new);
        user = userService.findById(creditDetails.getId()).orElseThrow(NotFoundException::new);
        Book book = service.findById(id).orElseThrow(NotFoundException::new);
        if (!user.getBooks().contains(book)) {
            if (creditDetails.getSumOfMoney().subtract(book.getPrice()).longValueExact() < 0) {
                throw new NotEnoughMoneyException();
            }
            creditDetails.setSumOfMoney(creditDetails.getSumOfMoney().subtract(book.getPrice()));
            user.getBooks().add(book);
            userService.save(user);
        }

        File file = new File(book.getUrl());
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);

    }


}
