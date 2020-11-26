package com.loneliness.service;

import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.News;
import com.loneliness.repository.AuthorRepository;
import com.loneliness.repository.NewsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
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
        return ((NewsRepository)repository).getTop4ByIdIsNotNullOrderByPrintTimeDesc();
    }
    public Optional<List<News>> getNodes(int page, int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"title");
       return ((NewsRepository)repository).findAllByIdIsNotNullOrderByTitle(
                PageRequest.of(page, size, sort ));
    }

}
