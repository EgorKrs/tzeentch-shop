package com.loneliness.service;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.exception.NotFoundException;
import com.loneliness.repository.NewsRepository;
import com.loneliness.repository.ReviewRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ReviewService extends CRUDService<Review>{
 private final UserService userService;
private final BookService bookService;
    private final NewsService newsService;
    public ReviewService(ReviewRepository repository, SearchService searchService,UserService userService,BookService bookService,
                         NewsService newsService){
        this.repository = repository;
        this.searchService = searchService;
        this.userService = userService;
        this.bookService =  bookService;
        this.newsService = newsService;
    }
    public Optional<List<Review>> findByNewsReview_Id(Integer newsReview_id){
        return ((ReviewRepository)repository).findByNewsReview_Id(newsReview_id);
    }
    public Optional<List<Review>> findByNewsReview_IdOrderByDataDesc(Integer newsReview_id){

        Optional<List<Review>> optionalReviews  = ((ReviewRepository)repository).findByNewsReviewIsNotNullOrderByDataDesc();
        optionalReviews.ifPresent(reviews -> reviews.removeIf(review -> review.getNewsReview().stream().noneMatch(news -> news.getId().equals(newsReview_id))));
//        return ((ReviewRepository)repository).findByNewsReview_IdOrderByDataDesc(newsReview_id);
        return optionalReviews;
    }
    public Optional<List<Review>> findByNewsReview_IdOrderByDataAsc(Integer newsReview_id){
//        return ((ReviewRepository)repository).findByNewsReview_IdOrderByDataAsc(newsReview_id);
        Optional<List<Review>> optionalReviews = ((ReviewRepository)repository).findByNewsReviewIsNotNullOrderByDataAsc();
        optionalReviews.ifPresent(reviews -> reviews.removeIf(review -> review.getNewsReview().stream().noneMatch(news -> news.getId().equals(newsReview_id))));
        return optionalReviews;
    }
    public Optional<List<Review>> findByNewsReview_IdOrderByDataDescMarkDesc(Integer newsReview_id){
//        return ((ReviewRepository)repository).findByNewsReview_IdOrderByDataDescMarkDesc(newsReview_id);
        Optional<List<Review>> optionalReviews = ((ReviewRepository)repository).findByNewsReviewIsNotNullOrderByDataDescMarkDesc();
//        Optional<List<Review>> optionalReviews = ((ReviewRepository)repository).findByNewsReviewIsNotNullOrderByDataDesc();
        optionalReviews.ifPresent(reviews -> reviews.removeIf(review -> review.getNewsReview().stream().noneMatch(news -> news.getId().equals(newsReview_id))));
        return optionalReviews;
    }
    public Review create(Review note){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User  user = (User) auth.getPrincipal();

        Integer answerId = note.getAnswers().iterator().next().getId();
        if (answerId == null) {
            note.getAnswers().clear();
        }

        Integer bookId = note.getSurveyedBook().iterator().next().getId();
        if (bookId== null) {
            note.getSurveyedBook().clear();
        }

        Integer newsId = note.getNewsReview().iterator().next().getId();
        if (newsId==null) {
            note.getNewsReview().clear();
        }
        if (note.getId() == null) {
            note.setAuthor(user);
            note.setData(Timestamp.valueOf(LocalDateTime.now()));
        }
        else {
            Review answer = new Review();
            answer.setComment(note.getComment());
            answer.setAuthor(user);
            answer.setData(Timestamp.valueOf(LocalDateTime.now()));
            save(answer);
            note = findById(note.getId()).get();
            note.getAnswers().add(answer);
            save(note);

        }//

        return save(note);
//        return new Review();
    }

}
