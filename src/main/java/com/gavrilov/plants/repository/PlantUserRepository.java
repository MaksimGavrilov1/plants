package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.PlantUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlantUserRepository extends JpaRepository<PlantUser, Long> {

    PlantUser findByUsername(String username);
}
