package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "violation")
@Getter
@Setter
public class Violation {
    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="site_id", nullable=false)
    @JsonBackReference
    private Site site;

    @ManyToOne
    @JoinColumn(name="container_id", nullable=false)
    @JsonBackReference
    private Container container;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String harvestUUID;

    private Timestamp timeOfViolation;
    private Boolean isChecked;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
