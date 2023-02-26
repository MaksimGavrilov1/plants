package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologicalMapDto {

    private String title;
    private Double temperatureMin;
    private Double temperatureMax;
    private Double humidityMin;
    private Double humidityMax;
    private Integer growthPeriod;
    private Long plantId;
    private List<MapConditionDto> conditions;
}
