package com.gavrilov.plants.service;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.ContainerDto;

import java.util.List;
import java.util.NoSuchElementException;

public interface ContainerService {

    public List<Container> findContainersBySite(Site site);

    public Container createContainer(ContainerDto containerDto, PlantUser user);

    Container getContainerById(Long id) throws NoSuchElementException;
}
