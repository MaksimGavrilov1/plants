package com.gavrilov.plants.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Task;
import com.gavrilov.plants.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@Transactional
public class TaskController {


    @Autowired
    private TaskRepository taskRepository;

    ObjectMapper parser = new ObjectMapper();

    @GetMapping("/tasks/all")
    public ResponseEntity<String> getAllTasks(@AuthenticationPrincipal PlantUser user) throws JsonProcessingException {
        List<Task> tasks = taskRepository.findBySite(user.getSite());
        return ResponseEntity.ok(parser.writeValueAsString(tasks));
    }

    @DeleteMapping("/task/delete")
    public ResponseEntity<String> deleteTasks(@AuthenticationPrincipal PlantUser user, @RequestBody Long taskId){
        Task harvestTask = taskRepository.findById(taskId).orElse(null);
        if (harvestTask != null) {
            if (harvestTask.getSite().equals(user.getSite())) {
                taskRepository.deleteByHarvestUUID(harvestTask.getHarvestUUID());
                return ResponseEntity.ok("");
            } else {
                return ResponseEntity.status(403).body("You have no access to this object");
            }
        } else {
            return ResponseEntity.status(404).body("Task not found");
        }
    }
}
