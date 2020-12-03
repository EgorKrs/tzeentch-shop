package com.loneliness.util.json_parser;

import com.loneliness.entity.domain.Genre;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


import java.io.IOException;

public class GenreDeserializer extends JsonDeserializer<Genre> {
    @Override
    public Genre deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final String jsonValue = parser.getText();
        for (final Genre genre : Genre.values()) {
            if (genre.getGenre().equals(jsonValue)) {
                return genre;
            }
        }
        return Genre.UNKNOWN;
    }
}
