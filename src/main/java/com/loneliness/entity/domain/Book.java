package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.TranslationStatus;
import com.loneliness.service.RatingParamService;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Component
@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"usersThatBoughtIt", "relatedBooks"})
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Domain {

    @Transient
//    @Autowired
    private static RatingParamService ratingParamService;

    @Autowired
    public void setRatingParamService(RatingParamService ratingParamService) {
        Book.ratingParamService = ratingParamService;
    }

    @PostConstruct
    public void init() {
        System.out.println(Book.ratingParamService + "was init");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank(groups = {Exist.class})
    @Null(groups = {New.class})
    private Integer id;
    private String name;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Picture picture;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "writtenBooks", fetch = FetchType.EAGER)
    private Set<Author> author;
    @NotEmpty(groups = {New.class,Exist.class})
//    @ElementCollection(targetClass = BookStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "book_status", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    @CollectionTable(name = "translation_status", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private TranslationStatus translationStatus;
    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "book_genre", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;
    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "books", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> usersThatBoughtIt;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Related_Book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "related_Books_id", referencedColumnName = "ID"))
    private Set<Book> relatedBooks;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Chapter> chapters;

//    @OneToMany(fetch = FetchType.EAGER,mappedBy = "surveyedBook")
//    @ManyToMany()
//    private List<Review> reviews;

    private Integer availability;
    //    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private RatingParam rating;
    @Transient
    private Double rating;

    private Integer popularity;

    private BigDecimal price;

    private String url;
    private String fileName;

    @PastOrPresent(groups = {New.class, Exist.class})
    private Timestamp printTime;

    @Override
    public Integer getId() {
        return id;
    }

    public Double getRating() {
        return ratingParamService.getAverageRating(this.id);
    }

    public void setRating(Double rating) {
        this.rating = getRating();
    }
}
