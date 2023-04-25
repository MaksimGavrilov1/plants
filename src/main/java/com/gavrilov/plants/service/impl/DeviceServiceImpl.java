package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.DeviceDto;
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
        //check device is not already taken
        List<String> ids = devices.stream().filter(x->x.getContainer() == null).map(Device::getDeviceId).toList();
        return ids;
    }

    @Override
    public List<Device> findAllBySite(Site site) {
        return deviceRepository.findBySite(site);
    }

    @Override
    public Device save(DeviceDto dto, PlantUser user) {
        Device device = new Device();
        device.setDeviceId(dto.getDeviceId());
        device.setDevicePassword(dto.getDevicePassword());
        device.setRegistryId(dto.getRegistryId());
        device.setRegistryPassword(dto.getRegistryPassword());
        device.setBrokerURL(dto.getBrokerURL());
        device.setOwner(user);
        device.setSite(user.getSite());
        return deviceRepository.save(device);
    }

    @Override
    public boolean isAbleTODelete(Device device) {
        return device.getContainer() == null;
    }
}
