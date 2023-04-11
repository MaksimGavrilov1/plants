package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByContainer(Container container);

    Device findByContainer_Id(Long id);


    Device findByDeviceId(String deviceId);

    List<Device> findByOwner(PlantUser owner);

    List<Device> findBySite(Site site);
}