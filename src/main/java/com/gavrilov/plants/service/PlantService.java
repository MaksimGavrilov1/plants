package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.PlantDto;

import java.util.List;
import java.util.Optional;

public interface PlantService {

    Plant findById (Long id);

    List<Plant> findBySite(Site site);

    Plant createPlant(PlantDto plantDto);

    Plant findByTitleAndSite(String title, Site site);
}
