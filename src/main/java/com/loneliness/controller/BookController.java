package com.loneliness.controller;


import com.loneliness.dto.BookDTO;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.Role;
import com.loneliness.entity.TranslationStatus;
import com.loneliness.entity.domain.*;
import com.loneliness.exception.BookNotAvailableException;
import com.loneliness.exception.NotEnoughMoneyException;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.AuthorService;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("book")
public class BookController extends CommonController<Book, BookDTO> {
    @Autowired
    private ServletContext servletContext;
    private UserCreditService userCreditService;
    private UserService userService;
    private AuthorService authorService;

    public BookController(BookService bookService, UserCreditService userCreditService, UserService userService, AuthorService authorService) {
        this.service = bookService;
        this.page = "book";
        this.userCreditService = userCreditService;
        this.userService = userService;
        this.authorService = authorService;
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
            model.put("CanEdit", user.getAuthorities().contains(Role.ADMIN));
            model.put("isAdmin", user.getRoles().contains(Role.ADMIN));

        } catch (ClassCastException ex) {
            model.put("Bought", false);
            model.put("CanEdit", false);
            model.put("isAdmin", false);
        }
        Optional<Book> book = service.findById(id);
        model.put("Book", book.orElse(new Book()));
        if (book.isPresent() && book.get().getRelatedBooks() != null) {
            model.put("RelatedBooks", JsonParser.mapToJson(book.get().getRelatedBooks()));
        } else model.put("RelatedBooks", JsonParser.mapToJson(new LinkedList<Book>()));
        if (book.isPresent() && book.get().getGenres() != null) {
            model.put("Genres", JsonParser.mapToJson(book.get().getGenres()));
        }
        if (book.isPresent() && book.get().getAuthor() != null) {
            model.put("Authors", JsonParser.mapToJson(book.get().getAuthor()));
        }
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            Integer numbOfBooks = user.getBooks().size();
            if (numbOfBooks > 20 && book.isPresent()) {
                book.get().setPrice(book.get().getPrice().subtract(book.get().getPrice().multiply(new BigDecimal("20")).divide(new BigDecimal("100"))));
            } else if (numbOfBooks > 10 && book.isPresent()) {
                book.get().setPrice(book.get().getPrice().subtract(book.get().getPrice().multiply(new BigDecimal("10")).divide(new BigDecimal("100"))));
            } else if (numbOfBooks > 5 && book.isPresent()) {
                book.get().setPrice(book.get().getPrice().subtract(book.get().getPrice().multiply(new BigDecimal("5")).divide(new BigDecimal("100"))));
            }
            if (user.getId() != null) {
                model.put("login", true);
            }
        } catch (ClassCastException ex) {
            model.put("login", false);
        }

        return page;
    }

    @Override
//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/change") //, consumes = MediaType.APPLICATION_JSON_VALUE
    public String changePage(@RequestParam(name = "id") Integer id, Map<String, Object> model) throws IOException {
        Object book = fillDomain(model, id);
        model.put("Authors", JsonParser.mapToJson(((Book) book).getAuthor()));
        model.put("Genres", JsonParser.mapToJson(((Book) book).getGenres()));
        model.put("AllGenre", JsonParser.mapToJson(Genre.values()));
        model.put("AllBookStatus", JsonParser.mapToJson(BookStatus.values()));
        model.put("AllTranslationStatus", JsonParser.mapToJson(TranslationStatus.values()));
        model.put("RelatedBook", JsonParser.mapToJson(((Book) book).getRelatedBooks()));
        model.put("AllBook", JsonParser.mapToJson(service.findAll()));
        model.put("AllAuthors", JsonParser.mapToJson(authorService.findAll()));
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            if (user.getId() != null) {
                model.put("login", true);
                model.put("isAdmin", user.getRoles().contains(Role.ADMIN));
            }
        } catch (ClassCastException ex) {
            model.put("login", false);
            model.put("isAdmin", false);
        }
        return page + "_edit";
    }

    @GetMapping(path = "/catalog")
    public String getAllPage(Map<String, Object> model,
                             @RequestParam(name = "genre", required = false) String genreString,
                             @RequestParam(name = "bookStatus", required = false) BookStatus bookStatus
    ) throws IOException {

        List<Book> nodes = new LinkedList<>();

        if (genreString != null || bookStatus != null) {
            final List<SearchCriteria> params = new ArrayList<>();
            if (genreString != null) {
                for (final Genre genre : Genre.values()) {
                    if (genre.getGenre().equals(genreString)) {
                        nodes = new LinkedList<>(((BookService) service).findAllByGenresContains(genre).orElse(new LinkedList<>()));
                    }
                }
            }
            if (bookStatus != null) {
                nodes = new LinkedList<>(((BookService) service).findAllByBookStatus(bookStatus));
            }

        } else {
            nodes = service.findAll();

        }
        model.put("AllGenre", JsonParser.mapToJson(Genre.values()));
        model.put("AllBookStatus", JsonParser.mapToJson(BookStatus.values()));
        model.put("Books", JsonParser.mapToJson(nodes));
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            if (user.getId() != null) {
                model.put("login", true);
            }
        } catch (ClassCastException ex) {
            model.put("login", false);
        }
        return "All" + page;
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @RequestParam(value = "id") Integer id) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        UserCreditDetails creditDetails = userCreditService.findById(user.getId()).orElseThrow(NotFoundException::new);
        user = userService.findById(creditDetails.getId()).orElseThrow(NotFoundException::new);
        Book book = service.findById(id).orElseThrow(NotFoundException::new);
        if (book.getAvailability().equals(0) || book.getBookStatus().name().equals(BookStatus.NOT_AVAILABLE.name())) {
            throw new BookNotAvailableException();
        }
        if (!user.getBooks().contains(book)) {
            int numbOfBooks = user.getBooks().size();
            if (numbOfBooks > 20) {
                book.setPrice(book.getPrice().subtract(book.getPrice().multiply(new BigDecimal("20")).divide(new BigDecimal("100"))));
            } else if (numbOfBooks > 10) {
                book.setPrice(book.getPrice().subtract(book.getPrice().multiply(new BigDecimal("10")).divide(new BigDecimal("100"))));
            } else if (numbOfBooks > 5) {
                book.setPrice(book.getPrice().subtract(book.getPrice().multiply(new BigDecimal("5")).divide(new BigDecimal("100"))));
            }
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

    @Override
//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public String createPage(@RequestParam(name = "name") String name, Map<String, Object> model)
            throws IOException {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        Picture picture = new Picture();
        picture.setUrl("../images/no-image.png");
        picture.setName("Enter name");
        picture.setId(-1);
        Book book = new Book();
        book.setId(0);
        book.setName("Enter name");
        book.setPopularity(0);
        book.setRating(0.0);
        book.setDescription("Enter descr");
        book.setPicture(picture);
        book.setAuthor(new HashSet<>());
        book.setRelatedBooks(new HashSet<>());
        book.setPrintTime(Timestamp.valueOf(LocalDateTime.now()));
        book.setBookStatus(BookStatus.NOT_AVAILABLE);
        book.setTranslationStatus(TranslationStatus.NOT_SET);
        book.setPrice(BigDecimal.ZERO);
        book.setUsersThatBoughtIt(new HashSet<>());
        book.setGenres(new HashSet<>());
        book.setFileName("Enter file name");
        book.setUrl("Enter Url");
        book.setAvailability(0);
        model.put(name, book);

        model.put("Authors", JsonParser.mapToJson(book.getAuthor()));
        model.put("Genres", JsonParser.mapToJson(book.getGenres()));
        model.put("AllGenre", JsonParser.mapToJson(Genre.values()));
        model.put("AllBookStatus", JsonParser.mapToJson(BookStatus.values()));
        model.put("AllTranslationStatus", JsonParser.mapToJson(TranslationStatus.values()));
        model.put("RelatedBook", JsonParser.mapToJson(book.getRelatedBooks()));
        model.put("AllBook", JsonParser.mapToJson(service.findAll()));
        model.put("AllAuthors", JsonParser.mapToJson(authorService.findAll()));
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            if (user.getId() != null) {
                model.put("login", true);
            }
        } catch (ClassCastException ex) {
            model.put("login", false);
        }
        return page + "_edit";
    }


}
