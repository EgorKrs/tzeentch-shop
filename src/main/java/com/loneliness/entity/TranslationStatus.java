package com.loneliness.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.loneliness.util.json_parser.TranslationStatusDeserializer;
import com.loneliness.util.json_parser.TranslationStatusSerializer;
import org.codehaus.jackson.annotate.JsonValue;

@JsonSerialize(using = TranslationStatusSerializer.class)
@JsonDeserialize(using = TranslationStatusDeserializer.class)
public enum TranslationStatus {

    FINISHED("Закончена"), CONTINUES("Продолжается"), REVIVE("Продолжается_после_остановки"), FREEZE("Заморожена"),
    ANNOUNCE("Анонсирована"), CANCELED("Отменена"), NOT_SET("Не_известно");
    private String value;

    TranslationStatus(String val) {
        this.value = val;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
