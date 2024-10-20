package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.model.Vehicle;

import java.util.List;

public interface VehicleService {

    public List<Vehicle> getVehicles() throws Exception;
    public Vehicle getVehicleById(Integer id) throws Exception;
    public Vehicle addVehicle (Vehicle vehicle) throws Exception;
    public Vehicle updateVehicle (Integer id, Vehicle vehicle) throws Exception;
    public void deleteVehicle (Integer id) throws Exception;
}
