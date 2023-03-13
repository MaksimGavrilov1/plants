package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.model.dto.DeviceDto;
import com.gavrilov.plants.model.dto.DeviceDtoRender;
import com.gavrilov.plants.repository.SensorDataRepository;
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
    private DeviceService deviceService;


    private final ObjectMapper parser = new ObjectMapper();

    @GetMapping("/data")
    public String getData() {
        PlantUser usr = new PlantUser();
        List<SensorData> data = sensorDataRepository.findAll();
        try {
            return parser.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.err.println("Unable to parse " + e.getMessage());
        }
        return "";
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
        deviceService.save(deviceDto, user);
        return ResponseEntity.ok("");
    }
}

