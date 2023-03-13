package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.SetupCell;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface SetupCellRepository extends JpaRepository<SetupCell, Long> {
}