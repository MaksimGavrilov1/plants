package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.PlantHistory;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.PlantHistoryRenderDto;
import com.gavrilov.plants.repository.PlantHistoryRepository;
import com.gavrilov.plants.service.PlantHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Qualifier("history")
@RequiredArgsConstructor
public class PlantHistoryServiceImpl implements PlantHistoryService {


    private final PlantHistoryRepository repository;


    @Override
    public List<PlantHistoryRenderDto> findBySiteAndConvertToRender(Site site) {
        List<PlantHistory> historyRows = repository.findBySiteOrderByDateOfPlantAsc(site);
        List<PlantHistory> distinctHarvests = historyRows.stream().filter(distinctByKey(PlantHistory::getHarvestId)).toList();
        List<PlantHistoryRenderDto> result = new ArrayList<>();
        for (PlantHistory example : distinctHarvests) {
            PlantHistoryRenderDto newRow = new PlantHistoryRenderDto();

            newRow.setHarvestUUID(example.getHarvestId());
            newRow.setContainerTitle(example.getSetup().getContainer().getTitle());
            newRow.setSetupAddress(example.getSetup().getAddress());
            newRow.setTechMapTitle(example.getMap().getTitle());
            newRow.setPlantTitle(example.getPlant().getTitle());
            newRow.setDateOfPlant(example.getDateOfPlant());
            newRow.setCellsIds(historyRows.stream().filter(x -> x.getHarvestId().equals(example.getHarvestId())).map(x -> x.getCell().getId()).toList());
            result.add(newRow);
        }
        return result;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
