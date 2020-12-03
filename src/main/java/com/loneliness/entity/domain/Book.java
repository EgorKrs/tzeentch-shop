package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.entity.BookStatus;
import com.loneliness.entity.Role;
import com.loneliness.entity.TranslationStatus;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Book implements Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank(groups = {Exist.class})
    @Null(groups = {New.class})
    private Integer id;
    private String name;
    @ManyToOne(cascade = CascadeType.REFRESH )
    private Picture picture;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany( cascade = CascadeType.REFRESH, mappedBy = "writtenBooks")
    private Set<Author> author;
    @NotEmpty(groups = {New.class,Exist.class})
//    @ElementCollection(targetClass = BookStatus.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "book_status", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    @CollectionTable(name = "translation_status", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private TranslationStatus translationStatus;
    @ElementCollection(targetClass = Genre.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "book_genre", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;
    @ManyToMany( cascade = CascadeType.REFRESH, mappedBy = "books")
    private Set<User> usersThatBoughtIt;
    private String description;
    @ManyToMany
    @JoinTable(name = "Related_Book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "related_Books_id", referencedColumnName = "ID"))
    private  Set<Book> relatedBooks;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Chapter> chapters;

//    @OneToMany(fetch = FetchType.EAGER,mappedBy = "surveyedBook")
    @ManyToMany()
    private List<Review> reviews;

    private Integer availability;

    private Integer popularity;

    private BigDecimal price;

    @PastOrPresent(groups = {New.class, Exist.class})
    private Timestamp printTime;

    @Override
    public Integer getId() {
        return id;
    }
}
