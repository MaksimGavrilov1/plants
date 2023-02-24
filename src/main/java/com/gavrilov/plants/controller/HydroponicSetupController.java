package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.HydroponicSetup;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.HydroponicSetupDto;
import com.gavrilov.plants.service.ContainerService;
import com.gavrilov.plants.service.HydroponicSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.ResourceEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class HydroponicSetupController {

    @Autowired
    private ContainerService containerService;
    @Autowired
    private HydroponicSetupService setupService;

    private final ObjectMapper parser = new ObjectMapper();

    @GetMapping("/setup/get/byContainer")
    public ResponseEntity<String> getSetupsByContainer(@AuthenticationPrincipal PlantUser user, @RequestBody Long id) {
        return ResponseEntity.ok("");
    }

    @PostMapping("/setup/create/{containerId}")
    public ResponseEntity<String> createSetup(@AuthenticationPrincipal PlantUser user, @RequestBody HydroponicSetupDto setup, @PathVariable Long containerId) {
        Container container = containerService.getContainerById(containerId);
        if (container != null) {
            if (user.getSite().equals(container.getSite())) {
                setupService.createSetup(setup, container);
                return ResponseEntity.ok("");
            } else {
                return ResponseEntity.status(403).body("No access to this object");
            }
        } else {
            return ResponseEntity.status(404).body("Container not found");
        }
    }

    @GetMapping("/setup/get/{setupId}")
    public ResponseEntity<String> viewSetup(@AuthenticationPrincipal PlantUser user, @PathVariable Long setupId) throws JsonProcessingException {
        HydroponicSetup setup = setupService.findSetup(setupId);
        if (setup != null) {
            if (user.getSite().equals(setup.getContainer().getSite())) {
                return ResponseEntity.ok(parser.writeValueAsString(setupService.convert(setup)));
            } else {
                return ResponseEntity.status(403).body("No access to this object");
            }
        } else {
            return ResponseEntity.status(404).body("Setup not found");
        }
    }
}
