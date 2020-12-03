package com.loneliness.util.json_parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.loneliness.entity.Role;
import com.loneliness.entity.domain.Genre;

import java.io.IOException;

public class GenreSerializer extends JsonSerializer<Genre> {

    @Override
    public void serialize(Genre genre, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(genre.getGenre());
    }
}