package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.PlantHistory;
import com.gavrilov.plants.model.Site;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public interface PlantHistoryRepository extends JpaRepository<PlantHistory, Long> {
    List<PlantHistory> findBySiteOrderByDateOfPlantAsc(Site site);
    List<PlantHistory> findByHarvestId(String harvestId);
}
