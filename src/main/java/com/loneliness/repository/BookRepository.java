package com.loneliness.repository;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.entity.domain.Room;
import com.loneliness.entity.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<List<Book>> getTop25ByIdIsNotNullOrderByPrintTimeDesc();
    Optional<List<Book>> findAllByGenresIsNotNull();

}
