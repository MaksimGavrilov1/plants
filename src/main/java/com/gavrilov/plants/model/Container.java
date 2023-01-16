package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "container")
@Getter
@Setter
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name="plant_user_id", nullable=false)
    @JsonBackReference
    private PlantUser user;

    @OneToMany(mappedBy="container")
    @JsonManagedReference
    private Set<HydroponicSetup> setups;
}
