package com.gavrilov.plants.quartz.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class RegularCheckJob implements Job {




    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("JOB IS SCHEDULED");
    }
}