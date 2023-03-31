package com.gavrilov.plants.service;

import com.gavrilov.plants.model.PlantHistory;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.PlantHistoryRenderDto;

import java.util.List;

public interface PlantHistoryService {

    List<PlantHistoryRenderDto> findBySiteAndConvertToRender(Site site);
}
