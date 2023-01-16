package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.repository.ContainerRepository;
import com.gavrilov.plants.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerServiceImpl implements ContainerService {

    @Autowired
    private ContainerRepository containerRepository;

    @Override
    public List<Container> findContainersByUser(PlantUser user) {
        List<Container> containers = containerRepository.findAll();
        List<Container> myCont = containerRepository.findByUser(user);
        return myCont;
    }
}
