package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantService {

    List<Plant> findAll ();

    Plant findById (Long id);
}
