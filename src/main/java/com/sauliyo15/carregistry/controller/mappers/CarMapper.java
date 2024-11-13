package com.sauliyo15.carregistry.controller.mappers;

import com.sauliyo15.carregistry.controller.dtos.CarRequest;
import com.sauliyo15.carregistry.controller.dtos.CarResponse;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CarMapper {

    public CarResponse toCarResponse(Car car) {
        CarResponse carResponse = new CarResponse();
        carResponse.setId(car.getId());
        carResponse.setBrand(car.getBrand().getName());
        carResponse.setModel(car.getModel());
        carResponse.setMilleage(car.getMilleage());
        carResponse.setPrice(car.getPrice());
        carResponse.setYear(car.getYear());
        carResponse.setDescription(car.getDescription());
        carResponse.setColour(car.getColour());
        carResponse.setFuelType(car.getFuelType());
        carResponse.setNumDoors(car.getNumDoors());
        return carResponse;
    }

    public Car toCar(CarRequest carRequest) {
        Car car = new Car();
        car.setBrand(Brand.builder().name(carRequest.getBrand()).build());
        car.setModel(carRequest.getModel());
        car.setMilleage(carRequest.getMilleage());
        car.setPrice(carRequest.getPrice());
        car.setYear(carRequest.getYear());
        car.setDescription(carRequest.getDescription());
        car.setColour(carRequest.getColour());
        car.setFuelType(carRequest.getFuelType());
        car.setNumDoors(carRequest.getNumDoors());
        return car;
    }

    public List<CarResponse> toCarListResponse(List<Car> cars) {
        return cars.stream().map(this::toCarResponse).toList();
    }
}
