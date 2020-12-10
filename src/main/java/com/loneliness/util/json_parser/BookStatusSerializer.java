package com.loneliness.util.json_parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.loneliness.entity.BookStatus;

import java.io.IOException;


public class BookStatusSerializer extends JsonSerializer<BookStatus> {
    @Override
    public void serialize(BookStatus bookStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(bookStatus.name());
    }
}
