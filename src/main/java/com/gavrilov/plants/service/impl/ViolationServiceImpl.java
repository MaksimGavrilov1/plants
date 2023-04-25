package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Violation;
import com.gavrilov.plants.model.dto.ViolationDto;
import com.gavrilov.plants.service.ViolationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViolationServiceImpl implements ViolationService {

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
            res.add(dto);
        }
        return res;
    }
}
