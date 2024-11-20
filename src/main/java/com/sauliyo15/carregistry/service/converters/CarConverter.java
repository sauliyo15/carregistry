package com.sauliyo15.carregistry.service.converters;

import com.sauliyo15.carregistry.entity.CarEntity;
import com.sauliyo15.carregistry.model.Car;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CarConverter {

    private final BrandConverter brandConverter;


    public CarConverter(BrandConverter brandConverter) {
        this.brandConverter = brandConverter;
    }

    public Car toCar(CarEntity carEntity) {
        Car car = new Car();
        car.setId(carEntity.getId());
        car.setBrand(brandConverter.toBrand(carEntity.getBrand()));
        car.setModel(carEntity.getModel());
        car.setMilleage(carEntity.getMilleage());
        car.setPrice(carEntity.getPrice());
        car.setYear(carEntity.getYear());
        car.setDescription(carEntity.getDescription());
        car.setColour(carEntity.getColour());
        car.setFuelType(carEntity.getFuelType());
        car.setNumDoors(carEntity.getNumDoors());
        return car;
    }

    public CarEntity toCarEntity(Car car) {
        CarEntity carEntity = new CarEntity();
        carEntity.setBrand(brandConverter.toBrandEntity(car.getBrand()));
        carEntity.setModel(car.getModel());
        carEntity.setMilleage(car.getMilleage());
        carEntity.setPrice(car.getPrice());
        carEntity.setYear(car.getYear());
        carEntity.setDescription(car.getDescription());
        carEntity.setColour(car.getColour());
        carEntity.setFuelType(car.getFuelType());
        carEntity.setNumDoors(car.getNumDoors());
        return carEntity;
    }

    public List<Car> toCarList(List<CarEntity> cars) {
        return cars.stream().map(this::toCar).toList();
    }
}
