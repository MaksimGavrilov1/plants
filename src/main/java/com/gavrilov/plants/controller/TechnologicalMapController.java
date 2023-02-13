package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.MapCondition;
import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.TechnologicalMap;
import com.gavrilov.plants.model.dto.MapConditionDto;
import com.gavrilov.plants.model.dto.TechnologicalMapDto;
import com.gavrilov.plants.repository.MapConditionRepository;
import com.gavrilov.plants.service.PlantService;
import com.gavrilov.plants.service.TechnologicalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class TechnologicalMapController {

    @Autowired
    private TechnologicalMapService mapService;
    @Autowired
    private PlantService plantService;
    @Autowired
    private MapConditionRepository conditionRepository;

    private final ObjectMapper parser = new ObjectMapper();

    @GetMapping("/maps/get/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id, @AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        TechnologicalMap map = mapService.findById(id);
        if (map.getPlant().getSite().equals(user.getSite())) {
            return ResponseEntity.ok(parser.writeValueAsString(map));
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body("No access to this object");
        }
    }

    @PostMapping("/maps/create/{plantId}")
    public ResponseEntity<String> createMap(@RequestBody TechnologicalMapDto map, @AuthenticationPrincipal PlantUser user, @PathVariable Long plantId) {
        Plant plant = plantService.findById(plantId);
        if (plant.getSite().equals(user.getSite())) {
            TechnologicalMap newMap = mapService.save(map, plant);
            MapCondition condition;
            for (MapConditionDto conditionDto : map.getConditions()) {
                condition = new MapCondition();
                condition.setDescription(conditionDto.getDescription());
                condition.setMap(newMap);
                conditionRepository.save(condition);
            }
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(403).body("No access to this object");
        }
    }
}
