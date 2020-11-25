package com.loneliness.dto;

import com.loneliness.entity.domain.*;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO implements DTO<News>{
    private Integer id;
    private String title;
    private String message;
    private Set<Review> reviews;
    private User author;
    private Picture picture;
    @PastOrPresent(groups = {New.class, Exist.class})
    private Timestamp printTime;

    @Override
    public News fromDTO() {
        News news = new News();
        news.setId(this.id);
        news.setTitle(title);
        news.setMessage(message);
        news.setReviews(reviews);
        news.setAuthor(author);
        news.setPicture(picture);
        news.setPrintTime(printTime);
        return news;
    }
}
