package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.PlantHistory;
import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.model.Task;
import com.gavrilov.plants.model.Violation;
import com.gavrilov.plants.model.enums.SensorDataStatus;
import com.gavrilov.plants.model.enums.TaskStatus;
import com.gavrilov.plants.repository.*;
import com.gavrilov.plants.service.JobService;
import com.gavrilov.plants.service.TechnologicalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private PlantHistoryRepository historyRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private TechnologicalMapService techMapService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ViolationRepository violationRepository;
    @Autowired
    private ContainerRepository containerRepository;

    @Override
    public void notifyOnHarvest(String harvestId, Long taskId) {
        Task harvestTask = taskRepository.findById(taskId).orElse(null);
        if (harvestTask != null) {
            harvestTask.setStatus(TaskStatus.HARVEST_DONE);
            taskRepository.save(harvestTask);
        }
    }

    @Override
    public void controlConditions(String harvestId, Long taskId) {
        Task controlTask = taskRepository.findById(taskId).orElse(null);
        List<PlantHistory> harvestRows = historyRepository.findByHarvestId(harvestId);
        PlantHistory example = harvestRows.stream().findFirst().orElse(null);
        if (example == null || controlTask == null) {
            return;
        }
        //String deviceId = deviceRepository.findByContainer(example.getSetup().getContainer()).getDeviceId();

        String deviceId = deviceRepository.findByContainer_Id(example.getContainerId()).getDeviceId();

        //get actual info
        SensorData actualData = sensorDataRepository.findByDeviceIdAndStatus(deviceId, SensorDataStatus.CONSTANT);
        //get info from tech map
        Map<String, Float> neededData = techMapService.getTempAndHumid(techMapService.findById(example.getMapId()));
        //get title to insert violation info
        StringBuilder sb = new StringBuilder();
        boolean isViolated = false;


        if (neededData.get("minTemp") > actualData.getTemperature()) {
            sb.append("Низкая температура.\n");
            isViolated = true;
        } else if (neededData.get("maxTemp") < actualData.getTemperature()) {
            sb.append("Высокая температура.\n");
            isViolated = true;
        }
        if (neededData.get("minHumid") > actualData.getHumidity()) {
            sb.append(" Низкая влажность.\n");
            isViolated = true;
        } else if (neededData.get("maxHumid") < actualData.getHumidity()) {
            sb.append(" Высокая влажность.\n");
            isViolated = true;
        }
        if (isViolated) {
            Violation violation = new Violation();
            violation.setHarvestUUID(harvestId);
            violation.setSite(controlTask.getSite());
            violation.setContainer(containerRepository.findById(example.getContainerId()).orElse(null));
            violation.setTimeOfViolation(new Timestamp(new Date().getTime()));
            violation.setMessage(sb.toString());
            violation.setIsChecked(false);
            violationRepository.save(violation);
        }


    }

    private String deleteValidations(StringBuilder sb) {
        sb.delete(sb.indexOf(" \nVIOLATIONS:\n "), sb.length());
        return sb.toString();
    }
}
