package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapConditionDtoRender {

    private String description;

    public MapConditionDtoRender(String description) {
        this.description = description;
    }
}
