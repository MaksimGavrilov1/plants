package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.ContainerDto;
import com.gavrilov.plants.repository.ContainerRepository;
import com.gavrilov.plants.service.ContainerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContainerServiceImpl implements ContainerService {

    @Autowired
    private ContainerRepository containerRepository;

    @Override
    @Transactional
    public List<Container> findContainersBySite(Site site) {
        List<Container> myCont = containerRepository.findBySite(site);
        return myCont;
    }

    @Override
    public Container createContainer(ContainerDto container, PlantUser user) {
        Container containerDB = new Container();
        containerDB.setDescription(container.getDescription());
        containerDB.setTitle(container.getTitle());
        containerDB.setSite(user.getSite());
        return containerRepository.save(containerDB);
    }

    @Override
    public Container getContainerById(Long id) throws NoSuchElementException {
        return containerRepository.findById(id).orElseThrow();
    }
}
