package com.gavrilov.plants.model;

import com.gavrilov.plants.model.enums.SensorDataStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Table(name = "sensor_data")
@Entity
@Getter
@Setter
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceId;
    private Float temperature;
    private Float humidity;
    private Timestamp time;
    @Enumerated(EnumType.STRING)
    private SensorDataStatus status;
}
