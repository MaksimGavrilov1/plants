package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;


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
    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    byte[] picture;

    @OneToMany(mappedBy="plant")
    @JsonManagedReference
    private List<SetupCell> plantedInCells;

    @OneToMany(mappedBy="plant")
    @JsonManagedReference
    private List<TechnologicalMap> maps;

    @OneToMany(mappedBy="plant")
    @JsonManagedReference
    private List<PlantHistory> historyRows;

    @ManyToOne
    @JoinColumn(name="site_id", nullable=false)
    @JsonBackReference
    private Site site;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
