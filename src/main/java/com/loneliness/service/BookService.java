package com.loneliness.service;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.entity.domain.Review;
import com.loneliness.repository.BookRepository;
import com.loneliness.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Transactional
@Service
public class BookService extends CRUDService<Book> {

    public BookService(BookRepository bookRepository, SearchService searchService){
        repository=bookRepository;
        this.searchService = searchService;
    }

    public Optional<List<Book>> getTop25ByIdIsNotNullOrderByPrintTimeDescPopularityDesc(){
        return ((BookRepository)repository).getTop25ByIdIsNotNullOrderByPrintTimeDesc();
    }
    public   Optional<List<Book>> findAllByGenresContains(Genre genreToFind){
        Optional<List<Book>> optionalBooks = ((BookRepository)repository).findAllByGenresIsNotNull();
        optionalBooks.ifPresent(books -> books.removeIf(book -> book.getGenres().stream().noneMatch(genre -> genre.equals(genreToFind))));
        return optionalBooks;
    }





}
