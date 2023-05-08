package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.HydroponicSetup;
import com.gavrilov.plants.model.Site;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public interface HydroponicSetupRepository extends JpaRepository<HydroponicSetup, Long> {
    List<HydroponicSetup> findByContainer_Site(Site site);
    long countByContainer_Site(Site site);

}
