package com.loneliness.entity.domain;

import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(of = { "id" })
public class News implements Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String message;
   // @OneToMany(fetch = FetchType.EAGER,mappedBy = "newsReview")
    @ManyToMany(mappedBy = "newsReview")
    private Set<Review> reviews;
    @ManyToOne(cascade = CascadeType.REFRESH )
    private User author;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Picture picture;
    @PastOrPresent(groups = {New.class, Exist.class})
    private Timestamp printTime;

}
