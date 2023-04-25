package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Plant;
import com.gavrilov.plants.model.SetupCell;

import java.util.List;

public interface SetupCellService {

    void updateCellsAfterHarvest(List<SetupCell> cells);

    boolean isAbleToDeletePlant(Plant plant);
}
