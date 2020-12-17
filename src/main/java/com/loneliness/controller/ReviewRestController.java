package com.loneliness.controller;

import com.loneliness.dto.ReviewDTO;
import com.loneliness.entity.domain.Review;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/edit/review")
public class ReviewRestController extends CommonRestController<Review, ReviewDTO> {

    public ReviewRestController(ReviewService service) {
        this.service = service;
        this.page = "review";
    }

    @GetMapping(value = "/get/review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Review> findByNewsReview_Id(@RequestParam(name = "id") Integer newsReviewId,
                                                  @RequestParam(name = "order", required = false) String sortOrder,
                                                  @RequestParam(name = "relatedType") String relatedType
    ) {
        switch (sortOrder) {
            case "asc":
                if (relatedType.equals("news"))
                    return ((ReviewService) service).findByNewsReview_IdOrderByDataAsc(newsReviewId).orElseThrow(NotFoundException::new);
                else
                    return ((ReviewService) service).findBySurveyedBookIsNotNullOrderByDataAsc(newsReviewId).orElseThrow(NotFoundException::new);
            case "pop":
                if (relatedType.equals("news"))
                    return ((ReviewService) service).findByNewsReview_IdOrderByDataDescMarkDesc(newsReviewId).orElseThrow(NotFoundException::new);
                else
                    return ((ReviewService) service).findBySurveyedBookIsNotNullOrderByDataDescMarkDesc(newsReviewId).orElseThrow(NotFoundException::new);
            default:
                if (relatedType.equals("news"))
                    return ((ReviewService) service).findByNewsReview_IdOrderByDataDesc(newsReviewId).orElseThrow(NotFoundException::new);
                else
                    return ((ReviewService) service).findBySurveyedBookIsNotNullOrderByDataDesc(newsReviewId).orElseThrow(NotFoundException::new);
        }
    }

    //    @PreAuthorize("hasAuthority('USER')")
    @PostMapping()
    @Override
    public Review create(@RequestBody ReviewDTO dto) throws IOException {
        return ((ReviewService) service).create(dto.fromDTO());
    }



}
