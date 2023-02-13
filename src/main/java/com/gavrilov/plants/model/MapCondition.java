package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "map_condition")
@Getter
@Setter
public class MapCondition {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;
    @ManyToOne
    @JoinColumn(name="technological_map_id", nullable=false)
    @JsonBackReference
    private TechnologicalMap map;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
