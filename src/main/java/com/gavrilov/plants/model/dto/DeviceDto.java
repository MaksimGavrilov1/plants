package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto {
    private String deviceId;
    private String registryId;
    private String registryPassword;
    private String brokerURL;
    private String devicePassword;
}
