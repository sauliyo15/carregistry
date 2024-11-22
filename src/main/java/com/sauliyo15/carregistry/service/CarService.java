package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.entity.CarEntity;
import com.sauliyo15.carregistry.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface CarService {

    CompletableFuture<List<Car>> getCars();
    Car getCarById(Integer id);
    Car addCar (Car car);
    Car updateCar (Integer id, Car car);
    void deleteCar (Integer id);
    CompletableFuture<List<Car>> addCars (List<Car> carList);
    String downloadCarsCsv() throws ExecutionException, InterruptedException;
    void uploadCarsCsv(MultipartFile file);
}
