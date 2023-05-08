package com.gavrilov.plants.repository;

import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.Task;
import com.gavrilov.plants.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    void deleteByHarvestUUID(String harvestUUID);
    List<Task> findByHarvestUUID(String harvestUUID);

    List<Task> findBySite(Site site);

    long countBySiteAndStatus(Site site, TaskStatus status);
}
