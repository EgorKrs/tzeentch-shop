package com.loneliness.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Review;
import com.loneliness.entity.domain.User;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO implements DTO<Review>{

    @Null(groups = New.class)
    @NotNull(groups = Exist.class)
    private Integer id;
    @NotNull(groups = {Exist.class,New.class})
    private String comment;
    @NotNull(groups = {Exist.class,New.class})
    @PositiveOrZero(groups = {Exist.class,New.class})
    private Integer mark;
    @NotNull(groups = {Exist.class,New.class})
    private User author;
    private Set<Book> surveyedBook;
    private Set<News> newsReview;
    private Set<Review> answers;
    private Timestamp data;

    @Override
    public Review fromDTO() {
        Review review = new Review();
        review.setId(id);
        review.setComment(comment);
        review.setMark(mark);
        review.setAuthor(author);
        review.setSurveyedBook(surveyedBook);
        review.setNewsReview(newsReview);
        review.setAnswers(answers);
        review.setData(data);
        return review;
    }
}
