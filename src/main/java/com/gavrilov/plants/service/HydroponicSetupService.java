package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.HydroponicSetup;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.dto.HydroponicSetupDto;
import com.gavrilov.plants.model.dto.HydroponicSetupDtoRender;
import com.gavrilov.plants.model.dto.PlantSeedDto;
import com.gavrilov.plants.model.dto.PlantSeedDtoRender;

public interface HydroponicSetupService {

    HydroponicSetup createSetup(HydroponicSetupDto dto, Container container);

    HydroponicSetup findSetup(Long id);

    HydroponicSetupDtoRender convert(HydroponicSetup setup);

    PlantSeedDtoRender convertToRender(HydroponicSetup setup);

    HydroponicSetup plantCulture(PlantSeedDto plantObject, PlantUser user, HydroponicSetup setup);

    boolean isAbleToDelete(HydroponicSetup setup);

}
