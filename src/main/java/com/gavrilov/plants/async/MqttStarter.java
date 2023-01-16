package com.gavrilov.plants.async;

import com.gavrilov.plants.mqtt.MqttBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MqttStarter {

    @Autowired
    public MqttBroker mqttBroker;

    @Async
    public void startBroker() {
        mqttBroker.Run(true);
    }
}
