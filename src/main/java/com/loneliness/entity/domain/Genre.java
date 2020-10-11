package com.loneliness.entity.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public enum  Genre  implements Domain{
    FANTASY, ADVENTURE, ROMANCE, CONTEMPORARY, DYSTOPIAN, MYSTERY, HORROR, THRILLER,
    PARANORMAL, HISTORICAL, FICTION, SCIENCE_FICTION, MEMOIR, COOKING, ART, SELF_HELP_OR_PERSONAL,
    DEVELOPMENT, MOTIVATIONAL, HEALTH, HISTORY, TRAVEL, GUIDE_HOW_TO, FAMILIES_AND_RELATIONSHIPS,
    HUMOR, CHILDREN_S;
    @Id
    private Integer id;
    @Override
    public Integer getId() {
        return 0;
    }
}
