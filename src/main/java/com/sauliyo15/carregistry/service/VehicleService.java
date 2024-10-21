package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.controller.dtos.VehicleRequest;
import com.sauliyo15.carregistry.controller.dtos.VehicleResponse;
import java.util.List;

public interface VehicleService {

    public List<VehicleResponse> getVehicles() throws Exception;
    public VehicleResponse getVehicleById(Integer id) throws Exception;
    public VehicleResponse addVehicle (VehicleRequest vehicleRequest) throws Exception;
    public VehicleResponse updateVehicle (Integer id, VehicleRequest vehicleRequest) throws Exception;
    public void deleteVehicle (Integer id) throws Exception;
}
