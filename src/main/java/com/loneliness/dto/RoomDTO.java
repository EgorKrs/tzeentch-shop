package com.loneliness.dto;

import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO implements DTO<Room> {
    private Integer id;
    @ManyToOne(cascade = CascadeType.REFRESH )
    private User author;
    private String title;
    private String message;
    private Set<Message> messages;
    @PastOrPresent(groups = {New.class, Exist.class})
    private Timestamp printTime;


    @Override
    public Room fromDTO() {
        return   new Room(id,author,title,message,printTime,messages);
    }
}
