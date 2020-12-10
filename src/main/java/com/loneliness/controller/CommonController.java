package com.loneliness.controller;

import com.loneliness.dto.DTO;
import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Domain;
import com.loneliness.entity.domain.Picture;
import com.loneliness.exception.BadArgumentException;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CommonController<T extends Domain,D extends DTO<T>>{
    protected String page;

    protected Service<T> service;


    @GetMapping()
    public String getOneById(@RequestParam(name = "id") Integer id, Map<String, Object> model) throws IOException {
        fillDomain(model, id);
        return page;
    }

    @GetMapping("/change")
    public String changePage(@RequestParam(name = "id") Integer id, Map<String, Object> model) throws IOException {
        fillDomain(model, id);
        return page + "_edit";
    }

    @GetMapping("/all")
    public String getAllPage(Map<String, Object> model) {
        List<T> nodes = service.findAll();
        model.put("allNodes", nodes);
        return "All" + page;
    }

    @GetMapping("/create")
    public String createPage(@RequestParam(name = "name") String name, Map<String, Object> model)
            throws IOException {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        Picture picture = new Picture();
        picture.setUrl("../images/no-image.png");
        picture.setName("Enter name");
        picture.setId(-1);
        switch (name) {
            case "Book":
                model.put(name, new Book());
                break;
            case "Picture":
                model.put(name, new Picture());
                break;
            case "Author":
                Author author = new Author();
                author.setId(0);
                author.setName("Enter ");
                author.setDescription("Enter description");
                author.setPicture(picture);
                author.setWrittenBooks(new HashSet<>());
                model.put(name,author);
                break;
            default: throw new BadArgumentException();
        }
        return page + "_edit";
    }

    protected Object fillDomain(Map<String, Object> model, Integer id) {
        Object data = find(id);
        model.put(data.getClass().getSimpleName(), data);
        return data;
    }

    protected T find(@PathVariable Integer id) {
        return service.findById(id).orElseThrow(NotFoundException::new);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errors.forEach((s, s2) -> System.out.println(s2));
        return errors;
    }

}
