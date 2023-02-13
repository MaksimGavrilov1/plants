package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologicalMapDto {

    private String title;
    private Integer temperatureMin;
    private Integer temperatureMax;
    private Integer humidityMin;
    private Integer humidityMax;
    private Integer growthPeriod;
    private Long plantId;
    private List<MapConditionDto> conditions;
}
