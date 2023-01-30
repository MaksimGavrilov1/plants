package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.ContainerDto;
import com.gavrilov.plants.repository.PlantUserRepository;
import com.gavrilov.plants.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
public class ContainerController {

    @Autowired
    private ContainerService containerService;
    @Autowired
    private PlantUserRepository plantUserRepository;
    private final ObjectMapper parser = new ObjectMapper();

    @GetMapping("/container/all")
    public ResponseEntity<String> findAll(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        List<Container> containers = containerService.findContainersByUser(user);
        if (containers != null) {
            try {
                return ResponseEntity.ok(parser.writeValueAsString(containers));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to parse containers list", e);
            }
        } else {
            return  ResponseEntity.status(HttpStatusCode.valueOf(500)).body("");
        }
    }

    @PostMapping("/container/create")
    public ResponseEntity<String> create(@RequestBody ContainerDto container, @AuthenticationPrincipal PlantUser user) {
        containerService.createContainer(container, user);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("");
    }
}
