package com.gavrilov.plants.controller;

import com.gavrilov.plants.model.PlantUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
public class HydroponicSetupController {

    @GetMapping("/setup/get/byContainer")
    public ResponseEntity<String> getSetupsByContainer(@AuthenticationPrincipal PlantUser user, @RequestBody Long id) {
        return ResponseEntity.ok("");
    }
}
