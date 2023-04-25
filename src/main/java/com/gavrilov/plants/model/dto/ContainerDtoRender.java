package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContainerDtoRender {

    private String title;
    private String description;
    private List<HydroponicSetupDtoRender> setups;
}
