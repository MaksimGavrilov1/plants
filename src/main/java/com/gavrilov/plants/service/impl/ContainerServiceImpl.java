package com.gavrilov.plants.service.impl;

import com.gavrilov.plants.model.Container;
import com.gavrilov.plants.model.Device;
import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.ContainerDto;
import com.gavrilov.plants.repository.ContainerRepository;
import com.gavrilov.plants.repository.DeviceRepository;
import com.gavrilov.plants.service.ContainerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContainerServiceImpl implements ContainerService {

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private DeviceRepository deviceRepository;

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
        Device device = deviceRepository.findByDeviceId(container.getDeviceId());
        containerDB.setDevice(device);
        Container fromDb = containerRepository.save(containerDB);
        device.setContainer(fromDb);
        deviceRepository.save(device);
        return fromDb;
    }

    @Override
    public Container getContainerById(Long id) throws NoSuchElementException {
        return containerRepository.findById(id).orElseThrow();
    }

    @Override
    public List<ContainerDto> convertContainers(List<Container> containers) {
        List<ContainerDto> result = new ArrayList<>();
        for (Container container:
             containers) {
            ContainerDto dtoObj = new ContainerDto();
            dtoObj.setTitle(container.getTitle());
            dtoObj.setDescription(container.getDescription());
            dtoObj.setDeviceId(container.getDevice().getDeviceId());
            dtoObj.setId(container.getId());
            result.add(dtoObj);
        }
        return result;
    }

    @Override
    public boolean isAbleToDelete(Container container) {
        return container.getSetups().size() == 0;
    }
}
