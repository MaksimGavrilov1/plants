package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.PlantHistory;
import com.gavrilov.plants.model.SensorData;
import com.gavrilov.plants.model.Task;
import com.gavrilov.plants.model.enums.SensorDataStatus;
import com.gavrilov.plants.model.enums.TaskStatus;
import com.gavrilov.plants.repository.DeviceRepository;
import com.gavrilov.plants.repository.PlantHistoryRepository;
import com.gavrilov.plants.repository.SensorDataRepository;
import com.gavrilov.plants.repository.TaskRepository;
import com.gavrilov.plants.service.JobService;
import com.gavrilov.plants.service.TechnologicalMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        StringBuilder sb = new StringBuilder(controlTask.getTitle().length() + 100);
        sb.append(controlTask.getTitle());
        if (!sb.toString().contains("VIOLATIONS")) {
            sb.append(" \nVIOLATIONS:\n ");
        }

        boolean violated = false;


        if (neededData.get("minTemp") > actualData.getTemperature()) {
            sb.append("LOW TEMPERATURE\n");
            violated = true;
        } else if (neededData.get("maxTemp") < actualData.getTemperature()) {
            sb.append("HIGH TEMPERATURE\n");
            violated = true;
        }
        if (neededData.get("minHumid") > actualData.getHumidity()) {
            sb.append("LOW HUMIDITY\n");
            violated = true;
        } else if (neededData.get("maxHumid") < actualData.getHumidity()) {
            sb.append("HIGH HUMIDITY\n");
            violated = true;
        }
        if (violated) {

            controlTask.setStatus(TaskStatus.CONTROL_WARNING);
            controlTask.setTitle(sb.toString());
            taskRepository.save(controlTask);
        } else {

            if (controlTask.getStatus().equals(TaskStatus.CONTROL_WARNING)) {
                controlTask.setStatus(TaskStatus.IN_PROGRESS);
                controlTask.setTitle(deleteValidations(sb));
                taskRepository.save(controlTask);
            }
        }

    }

    private String deleteValidations(StringBuilder sb) {
        sb.delete(sb.indexOf(" \nVIOLATIONS:\n "), sb.length());
        return sb.toString();
    }
}
