package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "setup_cell")
@Getter
@Setter
public class SetupCell {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer level;


    @ManyToOne
    @JoinColumn(name="plant_id")
    @JsonBackReference
    private Plant plant;

    @ManyToOne
    @JoinColumn(name="technological_map_id")
    @JsonBackReference
    private TechnologicalMap map;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="hydroponic_setup_id", nullable=false)
    @JsonBackReference
    private HydroponicSetup setup;
}
