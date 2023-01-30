package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}