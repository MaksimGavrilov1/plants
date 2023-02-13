package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.TechnologicalMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnologicalMapRepository extends JpaRepository<TechnologicalMap, Long> {
    List<TechnologicalMap> findByPlant(Plant plant);
}