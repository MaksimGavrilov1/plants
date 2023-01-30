package com.gavrilov.plants.service;

import com.gavrilov.plants.model.PlantUser;

import java.util.List;

public interface DeviceService {

    public List<String> getDevicesId(PlantUser user);
}
