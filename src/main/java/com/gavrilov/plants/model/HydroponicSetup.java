package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hydroponic_setup")
@Getter
@Setter
public class HydroponicSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="container_id", nullable=false)
    @JsonBackReference
    private Container container;



    @OneToMany(mappedBy="setup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SetupCell> levels;

}
