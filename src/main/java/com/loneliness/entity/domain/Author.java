package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id","name","description","picture"})
public class Author implements Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank(groups = {Exist.class})
    @Null(groups = {New.class})
    private Integer id;
    private String name;
    private String description;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Picture picture;
    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "authors_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonIgnore
    private Set<Book> writtenBooks;
}
