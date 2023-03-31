package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.model.enums.SensorDataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    List<SensorData> findByDeviceIdOrderByTimeAsc(String deviceId);
    SensorData findByDeviceIdAndStatus(String deviceId, SensorDataStatus status);
}
