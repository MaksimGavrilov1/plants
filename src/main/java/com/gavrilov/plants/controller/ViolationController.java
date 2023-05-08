package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Violation;
import com.gavrilov.plants.model.dto.ViolationDto;
import com.gavrilov.plants.repository.ViolationRepository;
import com.gavrilov.plants.service.ViolationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/violations/check")
    public ResponseEntity<String> checkViolation(@AuthenticationPrincipal PlantUser user, @RequestBody Long id) {
        Violation viol = repository.findById(id).orElse(null);
        if (viol != null){
            if (viol.getSite().equals(user.getSite())){
                viol.setIsChecked(true);
                repository.save(viol);
                return ResponseEntity.ok("");
            } else {
                return ResponseEntity.status(403).body("You are not authorized");
            }
        } else {
            return ResponseEntity.status(404).body("Violation not found");
        }
    }

    @GetMapping("/violations/stats")
    public ResponseEntity<String> getActiveNumber(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        List<Violation> viols = repository.findBySite(user.getSite());
        Long count = viols.stream().filter(x->!x.getIsChecked()).count();
        return ResponseEntity.ok(parser.writeValueAsString(count));
    }
}
