package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.SetupCell;
import com.gavrilov.plants.repository.SetupCellRepository;
import com.gavrilov.plants.service.SetupCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetupCellServiceImpl implements SetupCellService {

    @Autowired
    private SetupCellRepository repository;

    @Override
    public void updateCellsAfterHarvest(List<SetupCell> cells) {
        for (SetupCell cell:
             cells) {
            if (cell != null) {
                cell.setPlant(null);
                cell.setMap(null);
                repository.save(cell);
            }
        }
    }

    @Override
    public boolean isAbleToDeletePlant(Plant plant) {
        return repository.findByPlant(plant).size() == 0;
    }
}
