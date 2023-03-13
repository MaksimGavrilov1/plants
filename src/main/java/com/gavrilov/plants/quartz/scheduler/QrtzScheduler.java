package com.gavrilov.plants.quartz.scheduler;

import com.gavrilov.plants.quartz.AutoWiringSpringBeanJobFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='false'")
public class QrtzScheduler {


//    @Autowired
//    private ApplicationContext applicationContext;
//
//
//    @Bean
//    public SpringBeanJobFactory springBeanJobFactory() {
//        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
//
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }
//
//    @Bean
//    public Scheduler scheduler(SchedulerFactoryBean factory) throws SchedulerException {
//        Scheduler scheduler = factory.getScheduler();
////        scheduler.scheduleJob(job, trigger);
////
////        scheduler.start();
//        System.out.println("IN CONTROL");
//        scheduler.deleteJob(JobKey.jobKey("Qrtz_Job_Detail"));
//        return scheduler;
//    }
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(DataSource quartzDataSource) throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setJobFactory(springBeanJobFactory());
//        factory.setConfigLocation(new ClassPathResource("quartz.properties"));
//        factory.setDataSource(quartzDataSource);
//        return factory;
//    }
//
//    @Bean
//    @QuartzDataSource
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource quartzDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }
//
//    @Bean
//    public JobDetail jobDetail() {
//
//        return newJob().ofType(RegularCheckJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail")).withDescription("Invoke Sample Job service...").build();
//    }
//
//    @Bean
//    public Trigger trigger(JobDetail job) {
//
//        int frequencyInSec = 10;
//
//        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger")).withDescription("Sample trigger").withSchedule(simpleSchedule().withIntervalInSeconds(frequencyInSec).repeatForever()).build();
//    }
}
