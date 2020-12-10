package com.loneliness.controller;

import com.loneliness.dto.RoomDTO;
import com.loneliness.entity.domain.Room;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.RoomService;
import com.loneliness.validate_data.Exist;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/edit/forum")
public class ForumRestController extends CommonRestController<Room, RoomDTO> {
    public ForumRestController(RoomService service) {
        this.service = service;
        this.page = "forum";
    }

    @GetMapping("/getForums")
    public List<Room> getTopPage() {
        return ((RoomService) service).getTop4ByIdIsNotNullOrderByPrintTimeDesc().orElseThrow(NotFoundException::new);
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room update(@Validated(Exist.class) @RequestBody RoomDTO dto, @RequestParam(name = "id") Integer id) {
        if (id == 0) {
            return ((RoomService) service).update(dto.fromDTO());
        } else return service.save(dto.fromDTO());
    }
}