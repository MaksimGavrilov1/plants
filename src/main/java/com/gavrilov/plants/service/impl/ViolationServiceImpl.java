package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.Violation;
import com.gavrilov.plants.model.dto.ViolStatsDto;
import com.gavrilov.plants.model.dto.ViolationDto;
import com.gavrilov.plants.repository.ViolationRepository;
import com.gavrilov.plants.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ViolationServiceImpl implements ViolationService {

    @Autowired
    private ViolationRepository repository;

    @Override
    public List<ViolationDto> convert(List<Violation> viols) {
        List<ViolationDto> res = new ArrayList<>(viols.size());
        for (Violation viol:
             viols) {
            ViolationDto dto = new ViolationDto();
            dto.setId(viol.getId());
            dto.setContainerTitle(viol.getContainer().getTitle());
            dto.setHarvestUUID(viol.getHarvestUUID());
            dto.setMessage(viol.getMessage());
            dto.setTimeOfViolation(viol.getTimeOfViolation());
            dto.setIsChecked(viol.getIsChecked());
            res.add(dto);
        }
        return res;
    }

    @Override
    public List<ViolStatsDto> convertBySite(Site site) {
        List<Violation> viols = repository.findBySite(site);
        List<LocalDateTime> times = viols.stream().map(x -> x.getTimeOfViolation().toLocalDateTime()).toList();
        Map<LocalDate, Long> map = times.stream().collect(Collectors.groupingBy(LocalDateTime::toLocalDate, Collectors.counting()));
        List<ViolStatsDto> res = new ArrayList<>();
        for (Map.Entry<LocalDate, Long> entry : map.entrySet()) {
            ViolStatsDto stat = new ViolStatsDto();
            stat.setDate(Timestamp.valueOf(entry.getKey().atStartOfDay()));
            stat.setCount(entry.getValue());
            res.add(stat);
        }
        Collections.sort(res);
        return res;
    }
}
