package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologicalMapDtoRender {

    private String title;
    private Float temperatureMin;
    private Float temperatureMax;
    private Float humidityMin;
    private Float humidityMax;
    private Integer growthPeriod;
    private String plantTitle;
    private List<MapConditionDtoRender> conditions;

}
