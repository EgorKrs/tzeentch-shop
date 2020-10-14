package com.loneliness.service;

import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.repository.AuthorRepository;
import com.loneliness.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthorService extends CRUDService<Author> {

    public AuthorService(AuthorRepository authorRepository, SearchService searchService){
        repository = authorRepository;
        this.searchService = searchService;
    }



}
