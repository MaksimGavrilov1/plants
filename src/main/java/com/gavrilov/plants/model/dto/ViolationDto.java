package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ViolationDto {

    private Long id;
    private String harvestUUID;
    private String message;
    private String containerTitle;
    private Timestamp timeOfViolation;
}
