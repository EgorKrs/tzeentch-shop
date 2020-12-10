package com.loneliness.util.json_parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.loneliness.entity.BookStatus;

import java.io.IOException;


public class BookStatusDeserializer extends JsonDeserializer<BookStatus> {
    @Override
    public BookStatus deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        final String jsonValue = parser.getText();
        for (final BookStatus bookStatus : BookStatus.values()) {
            if (bookStatus.name().equals(jsonValue)) {
                return bookStatus;
            }
        }
        return BookStatus.NOT_SET;
    }
}
