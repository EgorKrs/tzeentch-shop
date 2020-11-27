package com.loneliness.repository;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
//    List<Message> findAllByRoomTitleOrderByDateDateDesc(String room_title);
//    Optional<List<Message>>findAllByRoomIsNotNullOrderByDateDateDesc();
    Optional<List<Message>> getAllByIdIsNotNullOrderByDateDesc();
//    List<Message> findAllByRoomTitle(String room_title);
}
