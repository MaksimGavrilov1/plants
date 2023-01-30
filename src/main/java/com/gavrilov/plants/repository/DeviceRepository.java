package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByOwner(PlantUser owner);
}