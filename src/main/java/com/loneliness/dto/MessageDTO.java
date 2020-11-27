package com.loneliness.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.loneliness.entity.MessageType;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO implements DTO<Message> {

    private Integer id;

    private User author;

    private String message;

    private Room room;

    private LocalDateTime date;
    private MessageType messageType;


    @Override
    public Message fromDTO() {
        Message newMessage = new Message();
        newMessage.setRoom(room);
        newMessage.setId(id);
        newMessage.setAuthor(author);
        newMessage.setMessage(message);
        newMessage.setDate(date);

        return newMessage;
    }
}
