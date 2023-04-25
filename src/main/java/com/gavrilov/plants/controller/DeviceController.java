package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.model.dto.DeviceDto;
import com.gavrilov.plants.model.dto.DeviceDtoRender;
import com.gavrilov.plants.repository.DeviceRepository;
import com.gavrilov.plants.repository.SensorDataRepository;
import com.gavrilov.plants.service.ContainerService;
import com.gavrilov.plants.service.DeviceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@Transactional
public class DeviceController {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private ContainerService containerService;
    @Autowired
    private DeviceService deviceService;


    public static final ObjectMapper parser = new ObjectMapper();
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/device/data/{containerId}")
    public ResponseEntity<String> getData(@AuthenticationPrincipal PlantUser user, @PathVariable Long containerId) throws JsonProcessingException {
        Container container = containerService.getContainerById(containerId);
        if (container != null) {
            if (user.getSite().equals(container.getSite())) {
                List<SensorData> data = sensorDataRepository.findByDeviceIdOrderByTimeAsc(container.getDevice().getDeviceId());
                return ResponseEntity.ok(parser.writeValueAsString(data));
            } else {
                return ResponseEntity.status(403).body("No access to this object");
            }
        } else {
            return ResponseEntity.status(404).body("Container not found");
        }
    }

    @GetMapping("/device/ids")
    public ResponseEntity<String> getDevicesIds(@AuthenticationPrincipal PlantUser user) {
        List<String> ids = deviceService.getDevicesId(user);
        try {
            return ResponseEntity.ok(parser.writeValueAsString(ids));
        } catch (JsonProcessingException e) {
            System.err.println("Unable to parse " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/device/all")
    public ResponseEntity<String> findAll(@AuthenticationPrincipal PlantUser user) {
        List<Device> allDevices = deviceService.findAllBySite(user.getSite());
        List<DeviceDtoRender> renderObject = new ArrayList<>();
        for (Device device:
             allDevices) {
            DeviceDtoRender temp = new DeviceDtoRender();
            temp.setId(device.getId());
            temp.setDeviceId(device.getDeviceId());
            temp.setRegistryId(device.getRegistryId());
            temp.setBrokerURL(device.getBrokerURL());
            renderObject.add(temp);
        }
        try {
            return ResponseEntity.ok(parser.writeValueAsString(renderObject));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @PostMapping("/device/add")
    public ResponseEntity<String> addDevice(@RequestBody DeviceDto deviceDto, @AuthenticationPrincipal PlantUser user) {
        Device device = deviceRepository.findByDeviceId(deviceDto.getDeviceId());
        if (device == null) {
            deviceService.save(deviceDto, user);
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.ok("false");
        }

    }

    @DeleteMapping("/device/delete/{id}")
    public ResponseEntity<String> deleteDevice(@AuthenticationPrincipal PlantUser user, @PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device != null) {
            if (device.getSite().equals(user.getSite())) {
                if (deviceService.isAbleTODelete(device)) {
                    deviceRepository.delete(device);
                    return ResponseEntity.ok("true");
                } else {
                    return ResponseEntity.ok("false");
                }
            } else {
                return ResponseEntity.status(403).body("You are not authorized");
            }
        } {
            return ResponseEntity.status(500).body("Internal Error");
        }
    }
}

