package com.gavrilov.plants.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Device {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String deviceId;
    @ManyToOne
    @JoinColumn(name = "plant_user_id")
    private PlantUser owner;

    private String devicePassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
