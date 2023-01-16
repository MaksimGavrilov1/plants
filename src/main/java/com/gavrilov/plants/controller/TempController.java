package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Role;
import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.repository.PlantUserRepository;
import com.gavrilov.plants.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
public class TempController {

    @Autowired
    private SensorDataRepository sensorDataRepository;


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
}
