package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CellDtoRender {

    private Long cellId;
    private String plantTitle;
    private Integer level;
    private String techMapTitle;
    private Timestamp dateOfPlant;
}
