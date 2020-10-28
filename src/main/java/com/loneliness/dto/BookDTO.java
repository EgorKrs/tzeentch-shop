package com.loneliness.dto;

import com.loneliness.entity.BookStatus;
import com.loneliness.entity.TranslationStatus;
import com.loneliness.entity.domain.*;

import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements DTO<Book> {

    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Integer id;
    private String name;
    private Picture picture;
    private Set<Author> author;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    private TranslationStatus translationStatus;
    private Set<Translater> translaters;
    private Set<Genre> genres;
    private Set<User> usersThatBoughtIt;
    private  Set<Book> relatedBooks;
    private Set<Chapter> chapters;
    private List<Review> reviews;
    private BigDecimal price;
    private Integer availability;

    public void setPrice(String price){
        this.price = new BigDecimal(price);
    }

    public Book fromDTO(){
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPicture(picture);
        book.setAuthor( author);
        book.setBookStatus(bookStatus );
        book.setTranslationStatus( translationStatus);
        book.setGenres(genres);
        book.setUsersThatBoughtIt(usersThatBoughtIt);
        book.setRelatedBooks( relatedBooks);
        book.setReviews(reviews);
        book.setAvailability(availability);
        book.setPrice(price);
        return book;
    }

}
