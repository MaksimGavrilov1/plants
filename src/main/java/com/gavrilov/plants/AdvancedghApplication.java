package com.gavrilov.plants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "com.gavrilov" })
@EnableScheduling
public class AdvancedghApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedghApplication.class, args);
    }

}
