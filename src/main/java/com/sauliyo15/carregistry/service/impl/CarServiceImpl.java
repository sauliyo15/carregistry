package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.CarEntity;
import com.sauliyo15.carregistry.exception.CarNotFoundException;
import com.sauliyo15.carregistry.exception.CarsNotFoundException;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.repository.CarRepository;
import com.sauliyo15.carregistry.service.BrandService;
import com.sauliyo15.carregistry.service.CarService;
import com.sauliyo15.carregistry.service.converters.CarConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final BrandService brandService;

    private final CarConverter carConverter;


    public CarServiceImpl(CarRepository carRepository, BrandService brandService, CarConverter carConverter) {
        this.carRepository = carRepository;
        this.brandService = brandService;
        this.carConverter = carConverter;
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> getCars() {
        long starTime = System.currentTimeMillis();
        List<CarEntity> carEntityList = carRepository.findAll();
        if (carEntityList.isEmpty()) {
            throw new CarsNotFoundException();
        }
        List<Car> carList = carConverter.toCarList(carEntityList);
        long endTime = System.currentTimeMillis();
        log.info("Total time elapsed Getting: {}", endTime-starTime);
        return CompletableFuture.completedFuture(carList);
    }

    @Override
    public Car getCarById(Integer id) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        CarEntity carEntity = carEntityOptional.orElseThrow(() -> new CarNotFoundException(id));
        return carConverter.toCar(carEntity);
    }

    public Car addCar(Car car) {
        Brand brand = brandService.getBrandByName(car.getBrand().getName());
        car.setBrand(brand);
        CarEntity carEntity = carConverter.toCarEntity(car);
        CarEntity savedEntity = carRepository.save(carEntity);
        return carConverter.toCar(savedEntity);
    }

    public Car updateCar(Integer id, Car car) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(id);
        }
        Brand brand = brandService.getBrandByName(car.getBrand().getName());
        car.setBrand(brand);
        CarEntity carEntity = carConverter.toCarEntity(car);
        carEntity.setId(id);
        return carConverter.toCar(carRepository.save(carEntity));
    }

    public void deleteCar(Integer id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(id);
        }
        carRepository.deleteById(id);
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> addCars(List<Car> carList) {
        long starTime = System.currentTimeMillis();
        List<CarEntity> carEntityList = new ArrayList<>();
        for (Car car : carList) {
            Brand brand = brandService.getBrandByName(car.getBrand().getName());
            car.setBrand(brand);
            carEntityList.add(carConverter.toCarEntity(car));
        }
        List<Car> carListSaved = carConverter.toCarList(carRepository.saveAll(carEntityList));
        long endTime = System.currentTimeMillis();
        log.info("Total time elapsed Getting: " + (endTime-starTime));
        return CompletableFuture.completedFuture(carListSaved);
    }
}

