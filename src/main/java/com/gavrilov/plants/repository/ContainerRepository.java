package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.PlantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContainerRepository extends JpaRepository<Container, Long> {
    List<Container> findByUser(PlantUser user);

}
