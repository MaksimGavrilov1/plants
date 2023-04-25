package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDtoRender {

    private Long id;
    private String deviceId;
    private String registryId;
    private String brokerURL;
}
