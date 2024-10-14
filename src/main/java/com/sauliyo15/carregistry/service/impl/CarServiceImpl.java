package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.repository.CarRepository;
import com.sauliyo15.carregistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    @Value("${property.nickname}")
    public String nickname;

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getCars() {
        log.info("El valor de la propiedad nickname es {}", nickname);
        return carRepository.getCars();
    }
}

