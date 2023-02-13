package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.TechnologicalMap;
import com.gavrilov.plants.model.dto.TechnologicalMapDto;

import java.util.List;

public interface TechnologicalMapService {

    List<TechnologicalMap> findByPlant(Plant plant);

    TechnologicalMap save(TechnologicalMapDto map, Plant plant);

    TechnologicalMap findById(Long id);
}
