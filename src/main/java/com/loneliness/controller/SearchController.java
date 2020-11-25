package com.loneliness.controller;

import com.loneliness.dto.SearchCriteriaDTO;
import com.loneliness.entity.domain.*;
import com.loneliness.exception.BadArgumentException;
import com.loneliness.service.SearchService;
import com.loneliness.util.json_parser.JsonParser;
import com.loneliness.util.search.SearchCriteria;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    @PostMapping(value = "/{tClass}",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public String search(@Validated @RequestBody SearchCriteriaDTO[] searchCriteriaDTO, @PathVariable String tClass) throws IOException {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        Arrays.stream(searchCriteriaDTO).forEach(searchCriteriaDTO1 -> searchCriteriaList.add(searchCriteriaDTO1.fromDTO()));
        switch (tClass) {
            case "book":
                return JsonParser.mapToJson(searchService.search(searchCriteriaList, Book.class));
            case "picture":
                return JsonParser.mapToJson(searchService.search(searchCriteriaList, Picture.class));
            case "author":
                return JsonParser.mapToJson(searchService.search(searchCriteriaList, Author.class));
            case "orders":
//                return JsonParser.mapToJson(searchService.search(searchCriteriaList, Orders.class));
            case "review":
                return JsonParser.mapToJson(searchService.search(searchCriteriaList, Review.class));
            case "user":
                return JsonParser.mapToJson(searchService.search(searchCriteriaList, User.class));
            case "news":
                return JsonParser.mapToJson(searchService.search(searchCriteriaList, News.class));
            default: throw new BadArgumentException();
        }

    }
}



