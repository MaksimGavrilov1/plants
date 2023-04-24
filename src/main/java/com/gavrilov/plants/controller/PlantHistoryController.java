package com.gavrilov.plants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.PlantHistoryRenderDto;
import com.gavrilov.plants.repository.PlantHistoryRepository;
import com.gavrilov.plants.service.PlantHistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gavrilov.plants.controller.DeviceController.parser;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RequiredArgsConstructor
public class PlantHistoryController {

    private final PlantHistoryService plantHistoryService;





    @GetMapping("/history/all")
    private ResponseEntity<String> getAllHistory(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {

        List<PlantHistoryRenderDto> result = plantHistoryService.findBySiteAndConvertToRender(user.getSite());
        if (result != null) {
            return ResponseEntity.ok(parser.writeValueAsString(result));
        } else {
            return ResponseEntity.status(404).body("History not found");
        }
    }
}
