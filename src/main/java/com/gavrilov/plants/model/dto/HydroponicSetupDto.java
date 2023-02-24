package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HydroponicSetupDto {
    private String address;
    private Integer levelsAmount;
    private Integer cellsAmount;
}
