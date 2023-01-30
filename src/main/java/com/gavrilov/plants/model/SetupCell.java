package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
