package com.sauliyo15.carregistry.repository.impl;

import com.sauliyo15.carregistry.model.Vehicle;
import com.sauliyo15.carregistry.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    // Lista simulada de coches
    List<Vehicle> vehiclesList = poblateCarList();


    @Override
    public List<Vehicle> getVehicles() throws Exception {
        log.info("Obteniendo la lista de coches");
        return vehiclesList;
    }

    @Override
    public Vehicle getVehicleById(Integer id) throws Exception {
        return vehiclesList.stream().filter(car -> car.getId() == id).findFirst().get();
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) throws Exception {
        vehicle.setId(vehiclesList.size() + 1);
        vehiclesList.add(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle updateVehicle(Integer id, Vehicle vehicle) throws Exception {
        Optional<Vehicle> vehic = vehiclesList.stream().filter(car -> car.getId().equals(id)).findFirst();

        if (vehic.isPresent()) {
            Vehicle existingVehicle = vehic.get();

            existingVehicle.setBrand(vehicle.getBrand());
            existingVehicle.setModel(vehicle.getModel());
            existingVehicle.setMilleage(vehicle.getMilleage());
            existingVehicle.setPrice(vehicle.getPrice());
            existingVehicle.setYear(vehicle.getYear());
            existingVehicle.setDescription(vehicle.getDescription());
            existingVehicle.setColour(vehicle.getColour());
            existingVehicle.setFuelType(vehicle.getFuelType());
            existingVehicle.setNumDoors(vehicle.getNumDoors());

            return existingVehicle;
        } else {
            throw new Exception("Vehículo no encontrado");
        }
    }

    @Override
    public void deleteVehicle(Integer id) throws Exception {
        vehiclesList.remove(vehiclesList.stream().filter(car -> car.getId() == id).findFirst().get());
    }


    private List<Vehicle> poblateCarList() {
        List<Vehicle> vehicleList = new ArrayList<>();

        vehicleList.add(new Vehicle(1, "Audi", "A4", 125519, 23.569, 2015, "Esta como nuevo", "Amarillo", "Gasoil", 5));

        vehicleList.add(Vehicle.builder()
                .id(2)
                .brand("BMW")
                .model("X6")
                .price(37.569)
                .year(2018)
                .build());
        log.info("Simulando la creación de una lista de coches");

        return  vehicleList;
    }
}

