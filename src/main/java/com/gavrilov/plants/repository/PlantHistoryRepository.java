package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.PlantHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface PlantHistoryRepository extends JpaRepository<PlantHistory, Long> {
}
