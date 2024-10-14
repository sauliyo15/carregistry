package com.sauliyo15.carregistry.repository.impl;

import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CarRepositoryImpl implements CarRepository {

    // Lista simulada de coches
    private List<Car> carList = new ArrayList<>();

    // Constructor para inicializar la lista de coches simulados
    public CarRepositoryImpl() {
        // Agrega algunos coches a la lista
        carList.add(new Car("Porsche", "Panamera", 2018));
        carList.add(new Car("Tesla", "Model S", 2020));
        carList.add(new Car("Audi", "A4", 2019));

        carList.add(Car.builder().brand("BMW").model("X6").build());
        log.info("Simulando la creación de una lista de coches");
    }

    @Override
    public List<Car> getCars() {
        log.info("Obteniendo la lista de coches");
        return carList;
    }
}

