package com.gavrilov.plants.service;

public interface JobService {

    void notifyOnHarvest(Long taskId, Long dateOfPlant, Integer growthTime, String harvestId);

    boolean areConditionsViolated(Long harvestId, Long taskId);
}
