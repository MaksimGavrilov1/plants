package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.PlantDto;
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
    public List<Plant> findBySite(Site site) {
        return plantRepository.findBySite(site);
    }

    @Override
    public Plant createPlant(PlantDto plantDto) {
        return null;
    }

    @Override
    public Plant findByTitleAndSite(String title, Site site) {
        return plantRepository.findByTitleAndSite(title, site);
    }

    @Override
    public Plant findById(Long id) {
        return plantRepository.findById(id).orElse(null);
    }
}
