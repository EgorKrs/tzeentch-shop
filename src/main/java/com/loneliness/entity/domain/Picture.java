package com.loneliness.entity.domain;

import com.loneliness.validate_data.Exist;
import com.loneliness.validate_data.New;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class Picture implements Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank(groups = {Exist.class})
    @Null(groups = {New.class})
    private Integer id;
    private String name;
    private String url;
}
