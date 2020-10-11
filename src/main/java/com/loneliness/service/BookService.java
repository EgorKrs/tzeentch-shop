package com.loneliness.service;

import com.loneliness.entity.domain.Book;
import com.loneliness.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class BookService extends CRUDService<Book> {

    public BookService(BookRepository bookRepository, SearchService searchService){
        repository=bookRepository;
        this.searchService = searchService;
    }





}
