package com.loneliness.service;

import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import com.loneliness.repository.RoomRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RoomService extends CRUDService<Room> {

    private UserService userService;

    public RoomService(RoomRepository repository, SearchService searchService, UserService userService) {
        this.repository = repository;
        this.searchService = searchService;
        this.userService = userService;
    }

    public Room findByTitle(String string) {
        Optional<Room> optionalRoom = ((RoomRepository) repository).findByTitle(string);
        Room room;
        if (optionalRoom.isEmpty()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            user.setActivationCode(null);
            user.setActive(true);
            user.setActivationCode(null);
            user.setGoogleId(null);
            room = new Room();
            room.setAuthor(user);
        } else {
            room = optionalRoom.get();
        }
        return room;
    }

    public Optional<List<Room>> getTop4ByIdIsNotNullOrderByPrintTimeDesc() {
        return ((RoomRepository) repository).getTop4ByIdIsNotNullOrderByPrintTimeDesc();
    }

    public Room update(Room room) {
        if (room.getId() <= 0) {
            room.setId(null);
            room.setMessages(null);
        }
        room.setAuthor(userService.findById(room.getAuthor().getId()).orElse(new User()));
        return save(room);
    }

}
