package com.loneliness.repository;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>  {
    Optional<List<News>> getTop4ByIdIsNotNullOrderByPrintTimeDesc();
}
