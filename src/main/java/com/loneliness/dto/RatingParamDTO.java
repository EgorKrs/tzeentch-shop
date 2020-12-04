package com.loneliness.dto;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.RatingParam;
import com.loneliness.entity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingParamDTO implements DTO<RatingParam> {
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User votedUser;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Book ratedBook;
    private Double rating;

    @Override
    public RatingParam fromDTO() {
        return new RatingParam(id, votedUser, ratedBook, rating);
    }
}
