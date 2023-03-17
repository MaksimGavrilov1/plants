package com.gavrilov.plants.quartz.scheduler;

import com.gavrilov.plants.service.JobService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HarvestJob implements Job {

    @Autowired
    private JobService jobService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        jobService.notifyOnHarvest(data.getString("harvestId"), Long.valueOf(data.getString("harvestTaskId")));
    }
}
