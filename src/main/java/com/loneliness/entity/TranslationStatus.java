package com.loneliness.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.loneliness.util.json_parser.TranslationStatusDeserializer;
import com.loneliness.util.json_parser.TranslationStatusSerializer;

@JsonSerialize(using = TranslationStatusSerializer.class)
@JsonDeserialize(using = TranslationStatusDeserializer.class)
public enum TranslationStatus {

    FINISHED, CONTINUES, REVIVE, FREEZE, ANNOUNCE, CANCELED, NOT_SET

}
