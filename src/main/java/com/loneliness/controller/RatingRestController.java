package com.loneliness.controller;

import com.loneliness.dto.RatingParamDTO;
import com.loneliness.entity.domain.RatingParam;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.BookService;
import com.loneliness.service.RatingParamService;
import com.loneliness.validate_data.New;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/edit/rating")
public class RatingRestController extends CommonRestController<RatingParam, RatingParamDTO> {
    private BookService bookService;

    public RatingRestController(RatingParamService service, BookService bookService) {
        this.service = service;
        this.page = "rating";
        this.bookService = bookService;
    }

    @Override
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public RatingParam create(@Validated(New.class) @RequestBody RatingParamDTO dto) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RatingParam ratingParam = dto.fromDTO();
        User user = (User) auth.getPrincipal();
        ratingParam.setVotedUser(user);
        ratingParam.setRatedBook(bookService.findById(ratingParam.getRatedBook().getId()).orElseThrow(NotFoundException::new));
        return service.save(ratingParam);
    }

}