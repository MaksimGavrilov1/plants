package com.gavrilov.plants.model.dto;

import com.gavrilov.plants.model.TechnologicalMap;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlantDtoRender {
    private Long id;
    private String title;
    private String description;
    String picture;
    List<TechnologicalMap> maps;
}
