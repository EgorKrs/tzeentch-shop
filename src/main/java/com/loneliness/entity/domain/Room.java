package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Domain{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private User author;
    @Column(unique=true)
    private String title;
    private String message;
    @PastOrPresent(groups = {New.class, Exist.class})
    private Timestamp printTime;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Message> messages;
}
