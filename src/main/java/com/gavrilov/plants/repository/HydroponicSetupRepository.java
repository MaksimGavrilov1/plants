package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.HydroponicSetup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
@Transactional
public interface HydroponicSetupRepository extends JpaRepository<HydroponicSetup, Long> {

}
