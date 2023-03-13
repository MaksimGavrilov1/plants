package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.model.enums.SensorDataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    SensorData findByDeviceIdAndStatus(String deviceId, SensorDataStatus status);
}
