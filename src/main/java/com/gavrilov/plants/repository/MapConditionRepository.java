package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.MapCondition;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface MapConditionRepository extends JpaRepository<MapCondition, Long> {
}