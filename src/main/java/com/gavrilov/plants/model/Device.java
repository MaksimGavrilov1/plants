package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "device")
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
    private String registryId;
    private String registryPassword;
    private String brokerURL;
    private String devicePassword;
    @ManyToOne
    @JoinColumn(name = "plant_user_id")
    @JsonBackReference
    private PlantUser owner;

    @OneToOne(mappedBy = "device")
    @JsonBackReference
    private Container container;

    @ManyToOne
    @JoinColumn(name="site_id", nullable=false)
    @JsonBackReference
    private Site site;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(deviceId, device.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
