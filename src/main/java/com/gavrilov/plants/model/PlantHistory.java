package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "plant_history")
@Getter
@Setter
public class PlantHistory {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="hydroponic_setup_id")
    @JsonBackReference
    private HydroponicSetup setup;
    private String harvestId;
    @ManyToOne
    @JoinColumn(name="setup_cell_id")
    @JsonBackReference
    private SetupCell cell;

    @ManyToOne
    @JoinColumn(name="plant_id")
    @JsonBackReference
    private Plant plant;

    @ManyToOne
    @JoinColumn(name="technological_map_id")
    @JsonBackReference
    private TechnologicalMap map;

    private Timestamp dateOfPlant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
