package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}