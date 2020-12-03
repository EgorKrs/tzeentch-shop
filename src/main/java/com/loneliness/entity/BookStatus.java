package com.loneliness.entity;

import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

public enum BookStatus {
    FINISHED, CONTINUES, REVIVE, FREEZE, ANNOUNCE, CANCELED, NOT_SET, NOT_AVAILABLE;

}
