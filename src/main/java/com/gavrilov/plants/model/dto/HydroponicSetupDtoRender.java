package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HydroponicSetupDtoRender {
    private Long setupID;
    private String address;
    private Integer levelsAmount;
    private Integer cellsPerLevel;
    private Integer freeCells;
    private Integer occupiedCells;
    private List<CellDtoRender> cells;
    private List<String> plantsTitles;
}
