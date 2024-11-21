package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.model.Car;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CarService {

    CompletableFuture<List<Car>> getCars();
    Car getCarById(Integer id);
    Car addCar (Car car);
    Car updateCar (Integer id, Car car);
    void deleteCar (Integer id);
    CompletableFuture<List<Car>> addCars (List<Car> carList);
}
