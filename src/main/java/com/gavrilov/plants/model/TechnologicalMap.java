package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "technological_map")
@Getter
@Setter
public class TechnologicalMap {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String temperatureMin;
    private String temperatureMax;
    private String humidityMin;
    private String humidityMax;
    private String growthPeriod;

    @ManyToOne
    @JoinColumn(name="plant_id", nullable=false)
    @JsonBackReference
    private Plant plant;

    @OneToMany(mappedBy="map")
    @JsonManagedReference
    private List<MapCondition> conditions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
