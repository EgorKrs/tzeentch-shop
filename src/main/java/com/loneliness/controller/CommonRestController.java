package com.loneliness.controller;

import com.loneliness.dto.DTO;
import com.loneliness.entity.domain.Domain;
import com.loneliness.exception.NotFoundException;
import com.loneliness.service.Service;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonRestController<T extends Domain, D extends DTO<T>> {
    protected String page;

    protected Service<T> service;


    @GetMapping()
    public String getOneById(@RequestParam(name = "id" , required = false ) Integer id , Map<String,Object> model) {
        Object data = find(id);
        model.put(data.getClass().getSimpleName(),data);
        return page;
    }


    //    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces =  MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public T create(@Validated(New.class) @RequestBody D dto) throws IOException {
        return service.save(dto.fromDTO());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('ADMIN')")
    public T update(@Validated(Exist.class) @RequestBody D dto, @RequestParam(name = "id") Integer id) {
        if (id != null && id > 0) {
            find(id);
        }
        return service.save(dto.fromDTO());
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@RequestParam(name = "id") Integer id) {
        service.delete(id);
        return "index";
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
