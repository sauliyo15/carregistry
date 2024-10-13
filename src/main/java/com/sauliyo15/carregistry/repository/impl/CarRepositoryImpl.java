package com.sauliyo15.carregistry.repository.impl;

import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CarRepositoryImpl implements CarRepository {

    @Override
    public void getCar() {

        Car car = new Car("Porsche", "Panamera", 2018);

        //Car car = new Car();
        //car.setBrand("Porsche");
        //car.setModel("Panamera");
        //car.setYear(2018);

        log.info("Los datos del vehículo son: {}", car.toString());

    }
}
