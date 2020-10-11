package com.loneliness.entity.domain;

import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class Translater {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank(groups = {Exist.class})
    @Null(groups = {New.class})
    private Integer id;
    private String fio;
    private String description;
    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "book_translater",
            joinColumns = @JoinColumn(name = "translater_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> translatedBooks;
    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "chapter_translater",
            joinColumns = @JoinColumn(name = "translater_id"),
            inverseJoinColumns = @JoinColumn(name = "chapter_id")
    )
    private Set<Chapter> translatedChapter;
}
