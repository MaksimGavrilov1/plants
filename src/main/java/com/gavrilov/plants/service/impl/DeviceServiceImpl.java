package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.repository.DeviceRepository;
import com.gavrilov.plants.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<String> getDevicesId(PlantUser user) {
        List<Device> devices = deviceRepository.findByOwner(user);
        List<String> ids = devices.stream().map(Device::getDeviceId).toList();
        return ids;
    }
}
