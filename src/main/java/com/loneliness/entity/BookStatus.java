package com.loneliness.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.loneliness.util.json_parser.BookStatusDeserializer;
import com.loneliness.util.json_parser.BookStatusSerializer;

@JsonSerialize(using = BookStatusSerializer.class)
@JsonDeserialize(using = BookStatusDeserializer.class)
public enum BookStatus {
    FINISHED, CONTINUES, REVIVE, FREEZE, ANNOUNCE, CANCELED, NOT_SET, NOT_AVAILABLE

}
