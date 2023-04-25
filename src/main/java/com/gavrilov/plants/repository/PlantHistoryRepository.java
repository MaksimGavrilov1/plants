package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.PlantHistory;
import com.gavrilov.plants.model.Site;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface PlantHistoryRepository extends JpaRepository<PlantHistory, Long> {
    List<PlantHistory> findBySiteOrderByDateOfPlantAsc(Site site);
    List<PlantHistory> findByHarvestId(String harvestId);

    @Query("select p from PlantHistory p where p.cellId = ?1")
    List<PlantHistory> findByCellId(Long cellId);

    @Query("select p from PlantHistory p where p.cellId = ?1 order by p.dateOfPlant DESC")
    List<PlantHistory> findByCellIdOrderByDateOfPlantDesc(Long cellId);




}
