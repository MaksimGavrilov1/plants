package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlantSeedDtoRender {

    private List<String> plantsTitles;
    private List<TechnologicalMapDtoRender> techMaps;
    private Integer freeCells;
    private String setupAddress;
}
