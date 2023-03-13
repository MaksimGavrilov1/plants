package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Table(name = "site")
@Entity
@Getter
@Setter
public class Site {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy="site")
    @JsonManagedReference
    private Set<PlantUser> users;

    @OneToMany(mappedBy="site")
    @JsonManagedReference
    private Set<Container> containers;

    @OneToMany(mappedBy="site")
    @JsonManagedReference
    private Set<Plant> plants;

    @OneToMany(mappedBy="site")
    @JsonManagedReference
    private Set<Device> devices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return id.equals(site.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
