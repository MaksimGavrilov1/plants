package com.gavrilov.plants.controller;

import com.gavrilov.plants.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
public class PlantsController {

    @Autowired
    private PlantService plantService;
}
