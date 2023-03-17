package com.gavrilov.plants.quartz.scheduler;

import com.gavrilov.plants.service.JobService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegularCheckJob implements Job {


    @Autowired
    private JobService jobService;
    public String harvestId;
    public Long controlTaskId;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        String desc = context.getJobDetail().getDescription();
        List<String> ids = List.of(desc.split(" "));
        jobService.controlConditions(ids.get(0), Long.valueOf(ids.get(1)));
    }
}