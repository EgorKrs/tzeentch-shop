package com.loneliness.controller;

import com.loneliness.dto.AuthorDTO;
import com.loneliness.dto.BookDTO;
import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.repository.AuthorRepository;
import com.loneliness.service.AuthorService;
import com.loneliness.util.json_parser.JsonParser;
import com.loneliness.util.search.SearchCriteria;
import com.loneliness.util.search.Searcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.loneliness.util.json_parser.JsonParser.mapFromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthorTestController {
    private String uri ="/authors";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AuthorController controller;

    @Autowired
    AuthorRepository repository;

    @Autowired
    AuthorService service;

    @Autowired
    private Searcher searcher;

    @Test
    public void AutowiredTest()throws Exception{
        assertThat(controller).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(service).isNotNull();
        assertThat(searcher).isNotNull();
    }


    @Test
    public void getAllTest()throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

    }
    @Test
    public void addValidTest() throws Exception{
        AuthorDTO dto = new AuthorDTO();
        dto.setDescription("description");
        dto.setFio("fio");
        final List<SearchCriteria> params = new ArrayList<>();
        params.add( SearchCriteria.builder().operation("=").key("id").value("1").build());
        List <Book> books =searcher.search(params,Book.class);
        dto.setWrittenBooks(new HashSet<>(books));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Author author = mapFromJson(result.getResponse().getContentAsString(), Author.class);
        assertEquals(service.findById(author.getId()).get(),author);
    }
    @Test
    public void addInValidTest()throws Exception{
        AuthorDTO dto = new AuthorDTO();
        dto.setDescription("description");
//        dto.setId(5);
        dto.setFio("");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                 .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getTest()throws Exception{
        int id =2;
        uri = uri + "/"+id;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

    }
    @Test
    public void deleteTest()throws Exception{
        int id =3;
        uri = uri +"/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, result.getResponse().getStatus());

        assertFalse(repository.findById(id).isPresent());

    }

    @Test
    public void updateTest()throws Exception{
        int id=4;
        AuthorDTO dto = new AuthorDTO();
        dto.setId(id);
        dto.setDescription("description");
        dto.setFio("fio");
        final List<SearchCriteria> params = new ArrayList<>();
        params.add( SearchCriteria.builder().operation("=").key("id").value("1").build());
        List <Book> books =searcher.search(params,Book.class);
        dto.setWrittenBooks(new HashSet<>(books));

        uri = uri + "/"+id;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonParser.mapToJson(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(service.findById(id).get(),dto.fromDTO());

    }
}
