package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Violation;
import com.gavrilov.plants.model.dto.ViolationDto;

import java.util.List;

public interface ViolationService {

    List<ViolationDto> convert(List<Violation> viols);
}
