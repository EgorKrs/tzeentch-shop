package com.loneliness.dto;

import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO implements DTO<Author> {
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Integer id;
    @NotBlank(groups = {Exist.class , New.class})
    private String fio;
    private String description;
    private Set<Book> writtenBooks;
    public Author fromDTO(){
    Author author = new Author();
    author.setId(id);
    author.setFio(fio);
    author.setDescription(description);
    author.setWrittenBooks(writtenBooks);
    return  author;
    }
}
