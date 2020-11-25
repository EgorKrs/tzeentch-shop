package com.loneliness.service;

import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.News;
import com.loneliness.repository.AuthorRepository;
import com.loneliness.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class NewsService extends CRUDService<News>{

    public NewsService(NewsRepository repository, SearchService searchService){
        this.repository = repository;
        this.searchService = searchService;
    }
    public Optional<List<News>>  getAllByIdIsNotNullOrderByPrintTimeDesc(){
        Optional<List<News>> list = ((NewsRepository)repository).getTop4ByIdIsNotNullOrderByPrintTimeDesc();
        return list;
    }

}
