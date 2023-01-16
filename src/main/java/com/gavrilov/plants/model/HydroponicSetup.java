package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hydroponic_setup")
@Getter
@Setter
public class HydroponicSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;

    @ManyToOne
    @JoinColumn(name="container_id", nullable=false)
    @JsonBackReference
    private Container container;
}
