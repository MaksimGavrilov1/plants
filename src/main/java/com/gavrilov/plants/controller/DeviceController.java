package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.repository.SensorDataRepository;
import com.gavrilov.plants.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
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
}

