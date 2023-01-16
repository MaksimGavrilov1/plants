package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.PlantUser;

import java.util.List;

public interface ContainerService {

    public List<Container> findContainersByUser(PlantUser user);
}
