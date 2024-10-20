package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.model.Vehicle;
import com.sauliyo15.carregistry.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehicles() {
        log.info("Obteniendo información de los coches");
        try {
            return ResponseEntity.ok(vehicleService.getVehicles());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
        }
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Integer id) {
        log.info("Obteniendo información del coche");
        try {
            return ResponseEntity.ok(vehicleService.getVehicleById(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
        }
    }

    @PostMapping("/vehicles")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) {
        log.info("Añadiendo coche");
        try {
            return ResponseEntity.ok(vehicleService.addVehicle(vehicle));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
        }
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Integer id, @RequestBody Vehicle vehicle) {
        log.info("Modificando coche");
        try {
            return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicle));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
        }
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id) {
        log.info("Eliminando coche");
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
        }
    }
}

