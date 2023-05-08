package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ViolStatsDto implements Comparable<ViolStatsDto> {

    private Timestamp date;
    private Long count;

    @Override
    public int compareTo(ViolStatsDto o) {
        return getDate().compareTo(o.getDate());
    }
}
