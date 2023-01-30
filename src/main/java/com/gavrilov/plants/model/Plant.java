package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "plant")
@Getter
@Setter
public class Plant {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String description;
    @Lob
    byte[] picture;

    @OneToMany(mappedBy="plant")
    @JsonManagedReference
    private List<SetupCell> plantedInCells;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}