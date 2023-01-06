package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
