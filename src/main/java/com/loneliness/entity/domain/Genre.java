package com.loneliness.entity.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.loneliness.util.json_parser.GenreDeserializer;
import com.loneliness.util.json_parser.GenreSerializer;
import org.codehaus.jackson.annotate.JsonValue;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import com.loneliness.util.json_parser.RoleDeserializer;
//import com.loneliness.util.json_parser.RoleSerializer;

@JsonSerialize(using = GenreSerializer.class)
@JsonDeserialize(using = GenreDeserializer.class)
public enum Genre implements Domain {
    FANTASY("Фентези"), ADVENTURE("Приключения"), ROMANCE("Романтика"), CONTEMPORARY("Современность"), DYSTOPIAN("антиутопия"),
    MYSTERY("мистика"), HORROR("ужасы"), THRILLER("триллер"), PARANORMAL("паронормальное"), HISTORICAL("историческое"),
    FICTION("вымысел"), SCIENCE_FICTION("научная_фантастика"), MEMOIR("мемуары"), COOKING("готовка"), ART("исскуство"),
    SELF_HELP_OR_PERSONAL("помощь"), DEVELOPMENT("разработка"), MOTIVATIONAL("мотивация"), HEALTH("здоровье"),
    HISTORY("историческое"), TRAVEL("путешествия"), GUIDE_HOW_TO("гайды"), FAMILIES_AND_RELATIONSHIPS("отношения"),
    HUMOR("юмор"), CHILDREN_S("детское"), UNKNOWN("_");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String value;

    Genre(String genre) {
        this.value = genre;
    }

    @Override
    public Integer getId() {
        return 0;
    }

    @JsonValue
    public String getGenre() {
        return value;
    }
}
