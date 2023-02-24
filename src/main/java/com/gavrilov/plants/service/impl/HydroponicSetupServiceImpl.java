package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.HydroponicSetup;
import com.gavrilov.plants.model.SetupCell;
import com.gavrilov.plants.model.dto.CellDtoRender;
import com.gavrilov.plants.model.dto.HydroponicSetupDto;
import com.gavrilov.plants.model.dto.HydroponicSetupDtoRender;
import com.gavrilov.plants.repository.HydroponicSetupRepository;
import com.gavrilov.plants.repository.SetupCellRepository;
import com.gavrilov.plants.service.HydroponicSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class HydroponicSetupServiceImpl implements HydroponicSetupService {

    @Autowired
    private HydroponicSetupRepository repository;
    @Autowired
    private SetupCellRepository cellRepository;

    @Override
    public HydroponicSetup createSetup(HydroponicSetupDto dto, Container container) {
        HydroponicSetup setup = new HydroponicSetup();
        setup.setAddress(dto.getAddress());
        setup.setContainer(container);
        HydroponicSetup fromDB = repository.save(setup);
        SetupCell cell;
        for (int i = 0; i < dto.getLevelsAmount(); i++) {
            for (int j = 0; j < dto.getCellsAmount(); j++) {
                cell = new SetupCell();
                cell.setLevel(i);
                cell.setSetup(fromDB);
                cellRepository.save(cell);
            }
        }
        return fromDB;
    }

    @Override
    public HydroponicSetup findSetup(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public HydroponicSetupDtoRender convert(HydroponicSetup setup) {
        HydroponicSetupDtoRender renderObject = new HydroponicSetupDtoRender();
        renderObject.setAddress(setup.getAddress());
        renderObject.setLevelsAmount(getNumberOfLevels(setup));
        renderObject.setCellsPerLevel(getCellsPerLevel(setup));
        renderObject.setFreeCells((int) setup.getLevels().stream().filter(x-> x.getPlant() == null).count());
        renderObject.setOccupiedCells((int) setup.getLevels().stream().filter(x->x.getPlant() != null).count());
        CellDtoRender cell;
        List<CellDtoRender> cells = new ArrayList<>();
        List<String> plantsTitles = new ArrayList<>();
        for (SetupCell cellDB : setup.getLevels()){
            cell = new CellDtoRender();
            String plantTitle = cellDB.getPlant() == null ? null : cellDB.getPlant().getTitle();
            cell.setPlantTitle(plantTitle);
            cell.setLevel(cellDB.getLevel());
            cells.add(cell);
            if (plantTitle != null) {
                plantsTitles.add(plantTitle);
            }
        }
        renderObject.setCells(cells);
        renderObject.setPlantsTitles(plantsTitles);
        return renderObject;
    }

    private Integer getNumberOfLevels(HydroponicSetup setup) {
        return Math.toIntExact(setup.getLevels().stream().filter(distinctByKey(SetupCell::getLevel)).count());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private Integer getCellsPerLevel(HydroponicSetup setup) {
        return Math.toIntExact(setup.getLevels().stream().filter(x -> x.getLevel().equals(1)).count());
    }


}
