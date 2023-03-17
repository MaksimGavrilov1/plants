package com.gavrilov.plants.async;

import jakarta.annotation.PostConstruct;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttStarterInitializer {

    @Autowired
    public MqttStarter mqttStarter;

    @Autowired
    public Scheduler scheduler;

    @PostConstruct
    public void initialize() throws SchedulerException {
//        JobDetail detail = newJob().ofType(RegularCheckJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail_2")).withDescription("Invoke Sample Job service...").build();
////        Trigger trigger = newTrigger().forJob(detail).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger_2")).withDescription("Sample trigger").withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
//        scheduler.deleteJobs(new ArrayList<JobKey>(List.of(new JobKey("Qrtz_Job_Detail_2"), new JobKey("Qrtz_Job_Detail"))));
    }
}
