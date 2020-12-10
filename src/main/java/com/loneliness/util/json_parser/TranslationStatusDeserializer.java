package com.loneliness.util.json_parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.loneliness.entity.TranslationStatus;

import java.io.IOException;


public class TranslationStatusDeserializer extends JsonDeserializer<TranslationStatus> {
    @Override
    public TranslationStatus deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        final String jsonValue = parser.getText();
        for (final TranslationStatus translationStatus : TranslationStatus.values()) {
            if (translationStatus.name().equals(jsonValue)) {
                return translationStatus;
            }
        }
        return TranslationStatus.NOT_SET;
    }
}