package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.ViolationDto;
import com.gavrilov.plants.repository.ViolationRepository;
import com.gavrilov.plants.service.ViolationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@Transactional
public class ViolationController {

    @Autowired
    private ViolationRepository repository;

    @Autowired
    private ViolationService violationService;

    private ObjectMapper parser = new ObjectMapper();

    @GetMapping("/violations")
    public ResponseEntity<String> getAllViolation(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        List<ViolationDto> data = violationService.convert(repository.findBySite(user.getSite()));
        return ResponseEntity.ok(parser.writeValueAsString(data));
    }
}
