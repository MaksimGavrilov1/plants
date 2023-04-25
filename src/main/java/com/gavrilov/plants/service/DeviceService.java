package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.DeviceDto;

import java.util.List;

public interface DeviceService {

    public List<String> getDevicesId(PlantUser user);

    List<Device> findAllBySite(Site site);

    Device save(DeviceDto dto, PlantUser user);

    boolean isAbleTODelete(Device device);
}
