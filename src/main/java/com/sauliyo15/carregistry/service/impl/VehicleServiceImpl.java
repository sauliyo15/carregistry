package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.controller.dtos.VehicleRequest;
import com.sauliyo15.carregistry.controller.dtos.VehicleResponse;
import com.sauliyo15.carregistry.model.Vehicle;
import com.sauliyo15.carregistry.repository.VehicleRepository;
import com.sauliyo15.carregistry.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<VehicleResponse> getVehicles() throws Exception {
        List<VehicleResponse> vehicleResponse = new ArrayList<>();
        vehicleRepository.getVehicles().forEach(vehicle -> vehicleResponse.add(toVehicleResponse(vehicle)));
        return vehicleResponse;
    }

    public VehicleResponse getVehicleById(Integer id) throws Exception{
        Vehicle vehicle = vehicleRepository.getVehicleById(id);
        return toVehicleResponse(vehicle);
    }

    public VehicleResponse addVehicle(VehicleRequest vehicleRequest) throws Exception{
        Vehicle vehicleSaved = vehicleRepository.addVehicle(toVehicle(vehicleRequest));
        return  toVehicleResponse(vehicleSaved);
    }

    public VehicleResponse updateVehicle(Integer id, VehicleRequest vehicleRequest) throws Exception{
        Vehicle vehicleUpdated = vehicleRepository.updateVehicle(id, toVehicle(vehicleRequest));
        return toVehicleResponse(vehicleUpdated);
    }

    public void deleteVehicle(Integer id) throws Exception {
        vehicleRepository.deleteVehicle(id);
    }


    // Mapper que convierte de Vehicle a VehicleResponse (para la respuesta)
    private VehicleResponse toVehicleResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .milleage(vehicle.getMilleage())
                .price(vehicle.getPrice())
                .year(vehicle.getYear())
                .description(vehicle.getDescription())
                .colour(vehicle.getColour())
                .fuelType(vehicle.getFuelType())
                .numDoors(vehicle.getNumDoors())
                .build();
    }

    // Mapper que convierte de VehicleRequest a Vehicle (para crear/actualizar)
    public Vehicle toVehicle(VehicleRequest request) {
        return Vehicle.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .milleage(request.getMilleage())
                .price(request.getPrice())
                .year(request.getYear())
                .description(request.getDescription())
                .colour(request.getColour())
                .fuelType(request.getFuelType())
                .numDoors(request.getNumDoors())
                .build();
    }
}

