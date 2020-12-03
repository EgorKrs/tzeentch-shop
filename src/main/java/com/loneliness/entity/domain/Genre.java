package com.loneliness.entity.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.loneliness.util.json_parser.GenreDeserializer;
import com.loneliness.util.json_parser.GenreSerializer;
import com.loneliness.util.json_parser.RoleDeserializer;
import com.loneliness.util.json_parser.RoleSerializer;
import lombok.Data;

import javax.persistence.*;

@JsonSerialize(using = GenreSerializer.class)
@JsonDeserialize(using = GenreDeserializer.class)
public enum  Genre  implements Domain{
    FANTASY, ADVENTURE, ROMANCE, CONTEMPORARY, DYSTOPIAN, MYSTERY, HORROR, THRILLER,
    PARANORMAL, HISTORICAL, FICTION, SCIENCE_FICTION, MEMOIR, COOKING, ART, SELF_HELP_OR_PERSONAL,
    DEVELOPMENT, MOTIVATIONAL, HEALTH, HISTORY, TRAVEL, GUIDE_HOW_TO, FAMILIES_AND_RELATIONSHIPS,
    HUMOR, CHILDREN_S,UNKNOWN;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Override
    public Integer getId() {
        return 0;
    }
    public String getGenre() {
        return name();
    }
}
