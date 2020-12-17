package com.loneliness.repository;

import com.loneliness.entity.domain.Book;
import com.loneliness.validate_data.Exist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<List<Book>> getTop25ByIdIsNotNullOrderByPrintTimeDesc();

    Optional<List<Book>> findAllByGenresIsNotNull();

    Optional<List<Book>> findDistinctByPrintTimeBetween(@PastOrPresent(groups = {Exist.class}) Timestamp printTime, @PastOrPresent(groups = {Exist.class}) Timestamp printTime2);
}
