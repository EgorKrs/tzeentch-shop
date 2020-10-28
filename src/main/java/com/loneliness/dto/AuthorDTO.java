package com.loneliness.dto;

import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Picture;
import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    private Picture picture;
    private String description;
    private Set<Book> writtenBooks;

    public Author fromDTO(){
    Author author = new Author();
    author.setId(id);
    author.setName(name);
    author.setDescription(description);
    author.setPicture(picture);
    author.setWrittenBooks(writtenBooks);
    return  author;
    }
}
