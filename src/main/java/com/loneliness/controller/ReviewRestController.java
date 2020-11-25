package com.loneliness.controller;

import com.loneliness.dto.NewsDTO;
import com.loneliness.dto.ReviewDTO;
import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.NewsService;
import com.loneliness.service.ReviewService;
import com.loneliness.service.UserService;
import com.loneliness.validate_data.New;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/edit/review")
public class ReviewRestController extends CommonRestController<Review, ReviewDTO> {

    public ReviewRestController(ReviewService service) {
        this.service = service;
        this.page = "review";
    }
    @GetMapping( value = "/get/newsReview",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public Collection<Review> findByNewsReview_Id(@RequestParam(name = "id" ) Integer newsReviewId,
                                                  @RequestParam(name = "order" , required = false ) String sortOrder){
        switch (sortOrder) {
//
            case "asc":
                return ((ReviewService) service).findByNewsReview_IdOrderByDataAsc(newsReviewId).orElseThrow(NotFoundException::new);
            case "pop":
                return ((ReviewService) service).findByNewsReview_IdOrderByDataDescMarkDesc(newsReviewId).orElseThrow(NotFoundException::new);
            default:
                return ((ReviewService) service).findByNewsReview_IdOrderByDataDesc(newsReviewId).orElseThrow(NotFoundException::new);
//
        }
    }
    @Override
    public Review create( @RequestBody ReviewDTO  dto) throws IOException {
        return ((ReviewService) service).create(dto.fromDTO());
    }



}
