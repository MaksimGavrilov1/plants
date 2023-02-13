package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.PlantDto;
import com.gavrilov.plants.model.dto.PlantDtoRender;
import com.gavrilov.plants.repository.PlantRepository;
import com.gavrilov.plants.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
public class PlantsController {

    @Autowired
    private PlantService plantService;

    private final ObjectMapper parser = new ObjectMapper();
    @Autowired
    private PlantRepository plantRepository;

    @GetMapping("/plants/all")
    public ResponseEntity<String> getAll(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        return ResponseEntity.ok(parser.writeValueAsString(plantService.findBySite(user.getSite())));
    }

    @PostMapping("/plants/create")
    public ResponseEntity<String> createPlant(@RequestBody PlantDto plant, @AuthenticationPrincipal PlantUser user) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] picture = encoder.encode(plant.getPicture().getBytes(StandardCharsets.UTF_8));
        Plant newPlant = new Plant();
        newPlant.setTitle(plant.getTitle());
        newPlant.setDescription(plant.getDescription());
        newPlant.setSite(user.getSite());
        newPlant.setPicture(picture);
        plantRepository.save(newPlant);
        return ResponseEntity.ok("");
    }

    @GetMapping("/plants/view/{id}")
    public ResponseEntity<String> viewPlant(@PathVariable Long id, @AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        Plant plant = plantService.findById(id);
        byte[] decodedPicture = Base64.getDecoder().decode(plant.getPicture());
        PlantDtoRender plantRender = new PlantDtoRender();
        plantRender.setId(plant.getId());
        plantRender.setTitle(plant.getTitle());
        plantRender.setDescription(plant.getDescription());
        plantRender.setPicture(new String(decodedPicture));
        plantRender.setMaps(plant.getMaps());
        if (plant.getSite().equals(user.getSite())) {
            return ResponseEntity.ok(parser.writeValueAsString(plantRender));
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("No access");
        }
    }
}
