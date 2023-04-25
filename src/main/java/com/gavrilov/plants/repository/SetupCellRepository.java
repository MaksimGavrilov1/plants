package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.SetupCell;
import com.gavrilov.plants.model.TechnologicalMap;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

@Transactional
public interface SetupCellRepository extends JpaRepository<SetupCell, Long> {
    List<SetupCell> findByPlant(Plant plant);

    @org.springframework.transaction.annotation.Transactional
    @Modifying
    @Query("update SetupCell s set s.plant = ?1, s.map = ?2 where s.id = ?3")
    void updateAfterHarvest(@Nullable Plant plant, @Nullable TechnologicalMap map, Long id);
}