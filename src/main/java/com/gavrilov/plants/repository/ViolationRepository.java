package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation, Long> {
    List<Violation> findBySite(Site site);

}
