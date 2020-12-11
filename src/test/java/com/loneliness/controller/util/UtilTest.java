package com.loneliness.controller.util;

import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Genre;
import com.loneliness.util.search.SearchCriteria;
import com.loneliness.util.search.Searcher;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {
    @Autowired
    private Searcher searcher;

    @Test
    public void searchListTest(){
        final List<SearchCriteria> params = new ArrayList<>();
        params.add( SearchCriteria.builder().operation("=").key("name").value("book1").build());
        List <Book> books =searcher.search(params, Book.class);
        books.forEach(System.out::println);
    }
    @Test
    public void searchAllTest(){
        final List<SearchCriteria> params = new ArrayList<>();
        params.add( SearchCriteria.builder().operation("like").key("id").value("").build());
        List <Author> books =searcher.search(params, Author.class);
        books.forEach(System.out::println);
    }
    @Test
    public void searchMasTest(){
        SearchCriteria[] params = new SearchCriteria[1];
        params[0] = SearchCriteria.builder().operation("=").key("name").value("book1").build();
        List <Book> books =searcher.search(params, Book.class);
        books.forEach(System.out::println);
    }
    @Test
    public void searchTest() {
        SearchCriteria[] params = new SearchCriteria[1];
        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.FANTASY);
        params[0] = SearchCriteria.builder().operation("=").key("genres").value(genres).build();
        List<Book> books = searcher.search(params, Book.class);
        books.forEach(System.out::println);
    }

}
