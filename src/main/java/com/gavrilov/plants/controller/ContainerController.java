package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.async.MqttStarter;
import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.HydroponicSetup;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.ContainerDto;
import com.gavrilov.plants.model.dto.ContainerDtoRender;
import com.gavrilov.plants.model.dto.HydroponicSetupDtoRender;
import com.gavrilov.plants.repository.ContainerRepository;
import com.gavrilov.plants.repository.PlantUserRepository;
import com.gavrilov.plants.service.ContainerService;
import com.gavrilov.plants.service.HydroponicSetupService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@Transactional
public class ContainerController {

    @Autowired
    private ContainerService containerService;
    @Autowired
    private PlantUserRepository plantUserRepository;
    @Autowired
    private MqttStarter starter;
    private final ObjectMapper parser = new ObjectMapper();
    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private HydroponicSetupService setupService;

    @GetMapping("/container/all")
    public ResponseEntity<String> findAll(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        List<Container> containers = containerService.findContainersBySite(user.getSite());
        if (containers != null) {
            try {
                return ResponseEntity.ok(parser.writeValueAsString(containerService.convertContainers(containers)));
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
        starter.startBroker(user.getSite());
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("");
    }

    @GetMapping("/container/view/{id}")
    public ResponseEntity<String> getContainer(@AuthenticationPrincipal PlantUser user, @PathVariable(name = "id") Long containerId){
            try {
                Container container = containerService.getContainerById(containerId);
                ContainerDtoRender render = new ContainerDtoRender();
                render.setTitle(container.getTitle());
                render.setDescription(container.getDescription());
                List<HydroponicSetupDtoRender> listRender = new ArrayList<>();
                for (HydroponicSetup setup:
                     container.getSetups()) {
                    HydroponicSetupDtoRender setRender = setupService.convert(setup);
                    listRender.add(setRender);
                }
                render.setSetups(listRender);
                if (container.getSite().equals(user.getSite())) {
                    return ResponseEntity.ok(parser.writeValueAsString(render));
                } else {
                    return ResponseEntity.status(403).body("No access to this container");
                }
            } catch (NoSuchElementException e){
                return ResponseEntity.status(404).body("No such container");
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(500).body("Problem occurred");
            }
    }

    @DeleteMapping("/container/delete/{id}")
    public ResponseEntity<String> deleteContainer (@AuthenticationPrincipal PlantUser user, @PathVariable Long id) {
        Container container = containerService.getContainerById(id);
        if (container.getSite().equals(user.getSite())) {
            if (containerService.isAbleToDelete(container)) {
                containerRepository.delete(container);
                return ResponseEntity.ok("true");
            } else {
                return ResponseEntity.ok("false");
            }
        } else {
            return ResponseEntity.status(403).body("You are not authorized to do this");
        }
    }
}
