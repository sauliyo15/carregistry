package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.CarEntity;
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

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CarConverter carConverter;


    @Override
    @Async
    public CompletableFuture<List<Car>> getCars() throws Exception {
        long starTime = System.currentTimeMillis();
        List<CarEntity> carEntityList = carRepository.findAll();
        if (carEntityList.isEmpty()) {
            throw new Exception("No cars found");
        }
        List<Car> carList = carConverter.toCarList(carEntityList);
        long endTime = System.currentTimeMillis();
        log.info("Total time elapsed Getting: " + (endTime-starTime));
        return CompletableFuture.completedFuture(carList);
    }

    @Override
    public Car getCarById(Integer id) throws Exception {
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        return carConverter.toCar(carEntityOptional.orElseThrow(() -> new Exception("Car not found with ID: " + id)));
    }

    public Car addCar(Car car) throws Exception {
        Brand brand = brandService.getBrandByName(car.getBrand().getName());
        car.setBrand(brand);
        return carConverter.toCar(carRepository.save(carConverter.toCarEntity(car)));
    }

    public Car updateCar(Integer id, Car car) throws Exception {
        carRepository.findById(id).orElseThrow(() -> new Exception("Car not found with ID: " + id));
        Brand brand = brandService.getBrandByName(car.getBrand().getName());
        car.setBrand(brand);
        CarEntity carEntity = carConverter.toCarEntity(car);
        carEntity.setId(id);
        return carConverter.toCar(carRepository.save(carEntity));
    }

    public void deleteCar(Integer id) throws Exception {
        carRepository.findById(id).orElseThrow(() -> new Exception("Car not found with ID: " + id));
        carRepository.deleteById(id);
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> addCars(List<Car> carList) throws Exception {
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

