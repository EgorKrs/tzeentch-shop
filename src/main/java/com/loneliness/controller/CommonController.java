package com.loneliness.controller;

import com.loneliness.dto.DTO;
import com.loneliness.entity.domain.Domain;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.Service;

import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonController<T extends Domain,D extends DTO<T>>{

    protected Service<T> service;
    @GetMapping
    public List<T> getAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public T getOneById(@PathVariable Integer id) {
        return find(id);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public T create(@Validated(New.class) @RequestBody D dto) throws IOException {
        return service.save(dto.fromDTO());
    }




    @PutMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
    public T update(@Validated(Exist.class) @RequestBody D dto, @PathVariable Integer id)  {
        find(id);
        return service.save(dto.fromDTO());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);

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
