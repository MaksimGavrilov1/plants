package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
