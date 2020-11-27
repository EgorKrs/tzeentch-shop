package com.loneliness.entity.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Message implements Domain {

    @Positive(message = "id MUST_BE_POSITIVE")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //    @NotNull(message = "userName must be set")
//    @Length(min = 3, message = "login MUST_HAVE_MORE_SYMBOLS_THEN 3")
//    @JsonProperty("username")
//    private String userName;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private User author;
    @Length(min = 1, message = "message MUST_BE ")
    @JsonProperty
    private String message;
    //    @Length(min = 1, message = "message MUST_BE ")
//    @JsonProperty
//    private String room;
    @NotNull(message = "date must be set")
    @PastOrPresent(message = "date MUST_BE_NOT_IN_FUTURE")
    @JsonProperty
    private LocalDateTime date;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "room_id")
    private Room room;


    public Message() {
    }

//    @JsonCreator
//    public Message(@JsonProperty("username") final User user,
//                   @JsonProperty("message") final String message, Room room) {
//        this.room = room;
//        Objects.requireNonNull(user);
//        Objects.requireNonNull(message);
//        id = 0;
//        this.author = user;
//        this.message = message.replace('\n', ' ').replace('\r', ' ');
//        date = LocalDateTime.now();
//    }

}

