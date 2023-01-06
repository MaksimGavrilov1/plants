package com.gavrilov.plants.async;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttStarterInitializer {

    @Autowired
    public MqttStarter mqttStarter;

    @PostConstruct
    public void initialize() {
        mqttStarter.startBroker();
    }
}
