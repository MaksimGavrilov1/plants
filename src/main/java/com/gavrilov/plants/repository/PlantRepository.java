package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.Site;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

@Transactional
public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findBySite(Site site);
}