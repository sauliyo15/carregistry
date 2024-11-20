package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.model.Car;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {

    CompletableFuture<List<Car>> getCars();
    Car getCarById(Integer id);
    Car addCar (Car car) throws Exception;
    Car updateCar (Integer id, Car car) throws Exception;
    void deleteCar (Integer id) throws Exception;
    CompletableFuture<List<Car>> addCars (List<Car> carList) throws Exception;
}
