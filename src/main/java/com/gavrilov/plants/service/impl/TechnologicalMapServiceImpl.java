package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.TechnologicalMap;
import com.gavrilov.plants.model.dto.TechnologicalMapDto;
import com.gavrilov.plants.repository.TechnologicalMapRepository;
import com.gavrilov.plants.service.TechnologicalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologicalMapServiceImpl implements TechnologicalMapService {

    @Autowired
    private TechnologicalMapRepository repository;

    @Override
    public List<TechnologicalMap> findByPlant(Plant plant) {
        return repository.findByPlant(plant);
    }

    @Override
    public TechnologicalMap save(TechnologicalMapDto map, Plant plant) {
        TechnologicalMap newMap = new TechnologicalMap();
        newMap.setTitle(map.getTitle());
        newMap.setTemperatureMax(String.valueOf(map.getTemperatureMax()));
        newMap.setTemperatureMax(String.valueOf(map.getTemperatureMin()));
        newMap.setTemperatureMax(String.valueOf(map.getHumidityMax()));
        newMap.setTemperatureMax(String.valueOf(map.getHumidityMin()));
        newMap.setPlant(plant);
        return repository.save(newMap);
    }

    @Override
    public TechnologicalMap findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
