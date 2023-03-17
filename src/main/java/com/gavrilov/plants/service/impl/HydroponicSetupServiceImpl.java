package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.*;
import com.gavrilov.plants.model.dto.*;
import com.gavrilov.plants.model.enums.TaskStatus;
import com.gavrilov.plants.quartz.scheduler.HarvestJob;
import com.gavrilov.plants.quartz.scheduler.RegularCheckJob;
import com.gavrilov.plants.repository.HydroponicSetupRepository;
import com.gavrilov.plants.repository.PlantHistoryRepository;
import com.gavrilov.plants.repository.SetupCellRepository;
import com.gavrilov.plants.repository.TaskRepository;
import com.gavrilov.plants.service.HydroponicSetupService;
import com.gavrilov.plants.service.PlantService;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.boot.MappingNotFoundException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class HydroponicSetupServiceImpl implements HydroponicSetupService {

    @Autowired
    private HydroponicSetupRepository repository;
    @Autowired
    private SetupCellRepository cellRepository;

    @Autowired
    private PlantHistoryRepository historyRepository;

    @Autowired
    private PlantService plantService;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Scheduler scheduler;

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
        renderObject.setFreeCells((int) setup.getLevels().stream().filter(x -> x.getPlant() == null).count());
        renderObject.setOccupiedCells((int) setup.getLevels().stream().filter(x -> x.getPlant() != null).count());
        CellDtoRender cell;
        List<CellDtoRender> cells = new ArrayList<>();
        List<String> plantsTitles = new ArrayList<>();
        List<SetupCell> cellsDB = setup.getLevels().stream().sorted(Comparator.comparing(SetupCell::getLevel).thenComparing(SetupCell::getId)).toList();
        for (SetupCell cellDB : cellsDB) {
            cell = new CellDtoRender();
            String plantTitle = cellDB.getPlant() == null ? null : cellDB.getPlant().getTitle();
            String mapTitle = cellDB.getMap() == null ? null : cellDB.getMap().getTitle();
            cell.setPlantTitle(plantTitle);
            cell.setLevel(cellDB.getLevel());
            cell.setTechMapTitle(mapTitle);
            cells.add(cell);
            if (plantTitle != null && !plantsTitles.contains(plantTitle)) {
                plantsTitles.add(plantTitle);
            }
        }
        renderObject.setCells(cells);
        renderObject.setPlantsTitles(plantsTitles);
        return renderObject;
    }

    @Override
    public PlantSeedDtoRender convertToRender(HydroponicSetup setup) {
        PlantSeedDtoRender renderObject = new PlantSeedDtoRender();
        List<Plant> plants = plantService.findBySite(setup.getContainer().getSite());
        List<TechnologicalMapDtoRender> maps = new ArrayList<>();
        plants.forEach(x -> x.getMaps().forEach(map -> maps.add(convertMap(map))));
        renderObject.setSetupAddress(setup.getAddress());
        renderObject.setPlantsTitles(plants.stream().map(Plant::getTitle).collect(Collectors.toList()));
        renderObject.setTechMaps(maps);
        renderObject.setFreeCells(100);
        int count = 0;
        for (SetupCell cell : setup.getLevels()) {
            if (cell.getPlant() == null) {
                count++;
            }
        }
        renderObject.setFreeCells(count);
        //renderObject.setFreeCells(Math.toIntExact(setup.getLevels().stream().filter(x -> x.getMap() == null).toList().size()));
        return renderObject;
    }

    @Override
    public HydroponicSetup plantCulture(PlantSeedDto plantObject, PlantUser user, HydroponicSetup setup) {
        Plant plant = plantService.findByTitleAndSite(plantObject.getPlantTitle(), user.getSite());
        TechnologicalMap map = plant.getMaps().stream().filter(x -> x.getTitle().equals(plantObject.getMapPlantTitle())).findFirst().orElse(null);
        List<SetupCell> newCells = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Timestamp dateOfPlant = new Timestamp(new Date().getTime());

        if (plant != null) {
            if (map != null) {
                List<SetupCell> cells = setup.getLevels().stream().filter(x -> x.getPlant() == null).sorted(Comparator.comparing(SetupCell::getLevel).thenComparing(SetupCell::getId)).toList();

                for (int i = 0; i < plantObject.getPlantAmount(); i++) {
                    PlantHistory historyRecord = new PlantHistory();
                    SetupCell cell = cells.get(i);
                    if (cell.getPlant() == null) {

                        //fill data for history
                        historyRecord.setSetup(setup);
                        historyRecord.setPlant(plant);
                        historyRecord.setMap(map);
                        historyRecord.setDateOfPlant(dateOfPlant);
                        historyRecord.setHarvestId(uuid.toString());
                        historyRecord.setSite(plant.getSite());


                        cell.setPlant(plant);
                        cell.setMap(map);
                        SetupCell fromDb = cellRepository.save(cell);
                        historyRecord.setCell(fromDb);
                        historyRepository.save(historyRecord);



                    }
                    //create tasks
                    Map<String, Long> taskIds = createTasks(uuid.toString());

                    //schedule job
                    try {
                        scheduleJobs(taskIds.get("controlId"), taskIds.get("harvestId"), uuid.toString(), Integer.valueOf(map.getGrowthPeriod()));
                    } catch (SchedulerException e) {
                        System.err.println("Unable to schedule jobs " + e.getMessage());
                    }
                }
                return setup;
            } else {
                throw new EntityNotFoundException("Plant doesn't have this map");
            }
        } else {
            throw new EntityNotFoundException("Plant not found");
        }
    }

    private void scheduleJobs(Long controlTaskId, Long harvestTaskId, String harvestId, Integer growthPeriod) throws SchedulerException {
        JobDataMap dataMapControl = new JobDataMap();
        dataMapControl.putAsString("controlTaskId", controlTaskId);
        dataMapControl.put("harvestId", harvestId);

        JobDataMap dataMapHarvest = new JobDataMap();
        dataMapHarvest.putAsString("harvestTaskId", harvestTaskId);
        dataMapHarvest.put("harvestId", harvestId);

        JobDetail controlJob = newJob()
                .ofType(RegularCheckJob.class)
                .storeDurably()
                .usingJobData("harvestId", harvestId)
                .usingJobData("controlTaskId", controlTaskId)
                .withIdentity(JobKey.jobKey(controlTaskId.toString()))
                .withDescription(harvestId + " " + controlTaskId)
                .setJobData(dataMapControl)
                .build();
        Trigger controlTrigger = newTrigger()
                .forJob(controlJob)
                .withIdentity(TriggerKey.triggerKey(controlTaskId.toString()))
                .withDescription("Trigger for control job")
                .withSchedule(simpleSchedule().withIntervalInSeconds(60 * 60).repeatForever())
                .build();
        JobDetail harvestJob = newJob()
                .ofType(HarvestJob.class)
                .storeDurably()
                .withIdentity(JobKey.jobKey(harvestTaskId.toString()))
                .withDescription("Harvest job for task with ID=%d".formatted(controlTaskId))
                .setJobData(dataMapHarvest)
                .build();
        Date date = new Date();
        date.setTime(new Date().getTime() + 1000L * 60 * 60 * 24 * growthPeriod);
        Trigger harvestTrigger = newTrigger()
                .forJob(harvestJob)
                .withIdentity(TriggerKey.triggerKey(harvestTaskId.toString()))
                .withDescription("Trigger for harvest job")
                .withSchedule(simpleSchedule().withRepeatCount(0))
                .startAt(date)
                .build();
        //growthPeriod * 24 * 60 * 60
        scheduler.scheduleJob(controlJob, controlTrigger);
        scheduler.scheduleJob(harvestJob, harvestTrigger);
    }

    private Map<String, Long> createTasks(String harvestId) {
        Map<String, Long> res = new HashMap<>();
        List<PlantHistory> examples = historyRepository.findByHarvestId(harvestId);
        PlantHistory example = examples.stream().findFirst().orElse(null);

        if (example == null) {
            return null;
        }

        Task harvestTask = new Task();
        harvestTask.setSite(example.getSite());
        harvestTask.setHarvestUUID(harvestId);
        harvestTask.setTitle("Harvest %s Planted: %s".formatted(example.getPlant().getTitle(), example.getDateOfPlant()));
        harvestTask.setStatus(TaskStatus.IN_PROGRESS);
        Task controlTask = new Task();
        controlTask.setSite(example.getSite());
        controlTask.setHarvestUUID(harvestId);
        controlTask.setTitle("Control temperature and humidity levels for plant: %s; Planted: %s".formatted(example.getPlant().getTitle(), example.getDateOfPlant()));
        controlTask.setStatus(TaskStatus.IN_PROGRESS);
        res.put("harvestId", taskRepository.save(harvestTask).getId());
        res.put("controlId", taskRepository.save(controlTask).getId());
        return res;
    }

    private TechnologicalMapDtoRender convertMap(TechnologicalMap map) {
        TechnologicalMapDtoRender renderMap = new TechnologicalMapDtoRender();
        renderMap.setTitle(map.getTitle());
        renderMap.setTemperatureMax(Float.valueOf(map.getTemperatureMax()));
        renderMap.setTemperatureMin(Float.valueOf(map.getTemperatureMin()));
        renderMap.setHumidityMax(Float.valueOf(map.getHumidityMax()));
        renderMap.setHumidityMin(Float.valueOf(map.getHumidityMin()));
//        renderMap.setGrowthPeriod(Integer.valueOf(map.getGrowthPeriod()));
        renderMap.setConditions(map.getConditions().stream().map(x -> new MapConditionDtoRender(x.getDescription())).collect(Collectors.toList()));
        renderMap.setPlantTitle(map.getPlant().getTitle());
        return renderMap;
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
