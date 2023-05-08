package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StatsDto {

    private String name;
    private String surname;
    private String middleName;
    private Long contNum;
    private Long plantNum;
    private Long setupNum;
    private List<ViolStatsDto> violations;
    private Integer busyPercent;
}
