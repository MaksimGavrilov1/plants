package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.repository.PlantRepository;
import com.gavrilov.plants.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public List<Plant> findAll() {
        return plantRepository.findAll();
    }

    @Override
    public Plant findById(Long id) {
        return plantRepository.findById(id).orElse(null);
    }
}
