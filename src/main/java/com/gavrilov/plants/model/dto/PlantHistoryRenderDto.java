package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PlantHistoryRenderDto {

    private String harvestUUID;
    private String containerTitle;
    private List<Long> cellsIds;
    private String plantTitle;
    private String techMapTitle;
    private String setupAddress;
    private Timestamp dateOfPlant;
}
