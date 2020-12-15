package com.loneliness.util.json_parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.loneliness.entity.TranslationStatus;

import java.io.IOException;

public class TranslationStatusSerializer extends JsonSerializer<TranslationStatus> {
    @Override
    public void serialize(TranslationStatus translationStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(translationStatus.getValue());
    }
}
