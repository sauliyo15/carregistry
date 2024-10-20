package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.model.Vehicle;
import com.sauliyo15.carregistry.repository.VehicleRepository;
import com.sauliyo15.carregistry.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getVehicles() throws Exception {
        return vehicleRepository.getVehicles();
    }

    public Vehicle getVehicleById(Integer id) throws Exception{
        return vehicleRepository.getVehicleById(id);
    }

    public Vehicle addVehicle(Vehicle vehicle) throws Exception{
        return vehicleRepository.addVehicle(vehicle);
    }

    public Vehicle updateVehicle(Integer id, Vehicle vehicle) throws Exception{
        return vehicleRepository.updateVehicle(id, vehicle);
    }

    public void deleteVehicle(Integer id) throws Exception {
        vehicleRepository.deleteVehicle(id);
    }


}

