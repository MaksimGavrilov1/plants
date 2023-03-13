package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Site;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface SiteRepository extends JpaRepository<Site, Long> {
}