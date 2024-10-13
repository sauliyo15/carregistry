package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<?> getCar() {
        log.info("-------------SE HA ACCEDIDO A LA APLICACION DE VEHICULOS-------------");
        carService.getCar();
        return ResponseEntity.ok("Bienvenido a la lista de vehículos");
    }
}
