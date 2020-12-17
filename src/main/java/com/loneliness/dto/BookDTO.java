package com.loneliness.dto;

import com.loneliness.entity.BookStatus;
import com.loneliness.entity.TranslationStatus;
import com.loneliness.entity.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements DTO<Book> {

    private Integer id;
    private String name;
    private Picture picture;
    private Set<Author> author;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    @Enumerated(EnumType.STRING)
    private TranslationStatus translationStatus;
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;
    private Set<User> usersThatBoughtIt;
    private String description;
    private Set<Book> relatedBooks;
    private Integer availability;
    private Double rating;
    private Integer popularity;
    private BigDecimal price;
    private String url;
    private String fileName;
    private Timestamp printTime;
    private BigDecimal discount;

    public void setPrice(String price) {
        this.price = new BigDecimal(price);
    }

    //    public Book fromDTO(){
//
//        return new Book(id,name,picture,author,bookStatus,translationStatus,genres,usersThatBoughtIt,
//                description,relatedBooks,availability,rating,popularity,price,"url","fileName",printTime);
//    }
    public Book fromDTO() {
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPicture(picture);
        book.setAuthor(author);
        book.setBookStatus(bookStatus);
        book.setTranslationStatus(translationStatus);
        book.setGenres(genres);
        book.setUsersThatBoughtIt(usersThatBoughtIt);
        book.setRelatedBooks(relatedBooks);
        book.setAvailability(availability);
        book.setPrice(price);
        book.setPrintTime(printTime);
        book.setDescription(description);
        book.setRating(rating);
        book.setPopularity(popularity);
        book.setUrl(url);
        book.setFileName(fileName);
        book.setDiscount(discount);

        return book;

    }
}
