package com.gavrilov.plants.async;

import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.mqtt.MqttBroker;
import com.gavrilov.plants.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MqttStarter {

    @Autowired
    public MqttBroker mqttBroker;

    @Autowired
    public SiteRepository repository;

    @Async
    public void startBroker(Site site) {
        mqttBroker.Run(true, site);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void startBrokerAfterShutDown() {
        List<Site> sites = repository.findAll();
        for (Site ex:
             sites) {
            mqttBroker.Run(true, ex);
        }

    }
}
