package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.StatsDto;
import com.gavrilov.plants.model.dto.ViolStatsDto;
import com.gavrilov.plants.model.dto.ViolationDto;
import com.gavrilov.plants.service.ContainerService;
import com.gavrilov.plants.service.HydroponicSetupService;
import com.gavrilov.plants.service.PlantService;
import com.gavrilov.plants.service.ViolationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@Transactional
public class SiteController {

    @Autowired
    private ContainerService containerService;
    @Autowired
    private PlantService plantService;
    @Autowired
    private HydroponicSetupService setupService;
    @Autowired
    private ViolationService violationService;

    ObjectMapper parser = new ObjectMapper();

    @GetMapping("/stats/all")
    public ResponseEntity<String> getStats(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        StatsDto data = new StatsDto();
        Long contNum = (long) containerService.findContainersBySite(user.getSite()).size();
        Long plantsNum = (long) plantService.findBySite(user.getSite()).size();
        Long setupNum = (long) setupService.findBySite(user.getSite());
        List<ViolStatsDto> viols = violationService.convertBySite(user.getSite());
        data.setContNum(contNum);
        data.setPlantNum(plantsNum);
        data.setSetupNum(setupNum);
        data.setViolations(viols);
        data.setBusyPercent(setupService.getBusyIndexBySite(user.getSite()));
        data.setName(user.getFirstName());
        data.setSurname(user.getLastName());
        data.setMiddleName(user.getMiddleName());
        return ResponseEntity.ok(parser.writeValueAsString(data));
    }

}
