package com.loneliness.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.loneliness.util.json_parser.BookStatusDeserializer;
import com.loneliness.util.json_parser.BookStatusSerializer;
import org.codehaus.jackson.annotate.JsonValue;

@JsonSerialize(using = BookStatusSerializer.class)
@JsonDeserialize(using = BookStatusDeserializer.class)
public enum BookStatus {
    FINISHED("Закончена"), CONTINUES("Продолжается"), REVIVE("Продолжается_после_остановки"), FREEZE("Заморожена"),
    ANNOUNCE("Анонсирована"), CANCELED("Отменена"), NOT_AVAILABLE("временно_недоступна");
    private String value;

    BookStatus(String val) {
        this.value = val;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
