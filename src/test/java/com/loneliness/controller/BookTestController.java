//package com.loneliness.controller;
//
//import com.loneliness.dto.AuthorDTO;
//import com.loneliness.dto.BookDTO;
//import com.loneliness.entity.BookStatus;
//import com.loneliness.entity.TranslationStatus;
//import com.loneliness.entity.domain.*;
//import com.loneliness.repository.BookRepository;
//import com.loneliness.service.BookService;
//import com.loneliness.util.json_parser.JsonParser;
//import com.loneliness.util.search.SearchCriteria;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.math.BigDecimal;
//import java.security.Timestamp;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static com.loneliness.util.json_parser.JsonParser.mapFromJson;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource("/application-test.properties")
//@Sql(value = {"/create-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/create-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//public class BookTestController {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    BookController bookcontroller;
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @Autowired
//    BookService bookService;
//
//    @Test
//    public void AutowiredTest()throws Exception{
//        assertThat(bookcontroller).isNotNull();
//        assertThat(mockMvc).isNotNull();
//        assertThat(bookService).isNotNull();
//    }
//
////    @Test
////    public void addValidTest()throws Exception {
////        BookDTO bookDTO = new BookDTO();
////        Author author = new Author();
////        author.setDescription("some info");
////        author.setFio("fio");
////
////        //author.setWrittenBooks();
////        Set<Author> authors = new HashSet<>() ;
////        authors.add(author);
////        bookDTO.setAuthor( authors);
////        bookDTO.setBookStatus(BookStatus.FINISHED);
////        bookDTO.setTranslationStatus( TranslationStatus.ANNOUNCE);
////
////        Set<Genre> genres = new HashSet<>();
////        genres.add(Genre.ADVENTURE);
////        genres.add(Genre.ROMANCE);
////        bookDTO.setGenres(genres);
////
////        Translater translater = new Translater();
////        translater.setDescription("description");
////        translater.setFio("Fio");
////        Set<Translater> translaters = new HashSet<>();
////        translaters.add(translater);
////        bookDTO.setTranslaters( translaters);
////
////        User user = new User();
////        user.setUsername("user");
////        user.setLocale("RU");
////        user.setGoogleId("id");
////        user.setActive(true);
////        user.setActivationCode("As");
////        user.setEmail("esda@gmai.com");
////        Set<User> users = new HashSet<>();
////        bookDTO.setUsersThatBoughtIt(users);
////
////        Chapter chapter = new Chapter();
////        chapter.setChapterText("chapter 1");
////        chapter.setNumberOfChapter(1);
////        chapter.setTranslaters(translaters);
////        Set<Chapter> chapters = new HashSet<>();
////        bookDTO.setChapters(chapters);
////
////        bookDTO.setAvailability(true);
////        bookDTO.setPrice("12.80");
////
////        Set<Book> books = new HashSet<>();
////        Book book = new Book();
////        book.setPrice(BigDecimal.valueOf(10));
////        book.setOriginalName("Oter name");
////        books.add(book);
////
////        Review review = new Review();
////        review.setMark(2);
////        review.setComment("shit");
////        review.setAuthor(user);
////        review.setSurveyedBook(book);
////        List<Review> reviews = new ArrayList<>();
////        bookDTO.setReviews(reviews);
////
////
//////        bookDTO.setRelatedBooks( books );
////        String uri ="/books";
////        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
////                .accept(MediaType.APPLICATION_JSON_VALUE)
////                .contentType(MediaType.APPLICATION_JSON_VALUE)
////                .content(JsonParser.mapToJson(bookDTO)))
////                .andReturn();
////        assertEquals(200, result.getResponse().getStatus());
////        Book bookQ = mapFromJson(result.getResponse().getContentAsString(), Book.class);
////        assertEquals(bookService.findById(bookQ.getId()).get(),bookQ);
////    }
////    @Test
////    public void searchTest()throws Exception{
////
////        final List<SearchCriteria> params = new ArrayList<>();
////        String id="1";
////        params.add( SearchCriteria.builder().operation("=").key("id").value(id).build());
////        String uri ="/search/Book";
////        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
////                .accept(MediaType.APPLICATION_JSON_VALUE)
////                .contentType(MediaType.APPLICATION_JSON_VALUE)
////                .content(JsonParser.mapToJson(params)))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
////                .andReturn();
////
////    }
////
////    @Test
////    public void updateTest() throws  Exception{
////
////    }
//}
