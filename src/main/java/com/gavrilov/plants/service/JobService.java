package com.gavrilov.plants.service;

public interface JobService {

    void notifyOnHarvest(String harvestId, Long taskId);

    void controlConditions(String harvestId, Long taskId);
}
