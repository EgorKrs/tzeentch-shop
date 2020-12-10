package com.loneliness.repository;

import com.loneliness.entity.domain.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByTitle(String title);

    Optional<List<Room>> getTop4ByIdIsNotNullOrderByPrintTimeDesc();

    Optional<List<Room>> findAllByIdIsNotNullOrderByTitle(Pageable pageable);
}
