package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.VehicleRequest;
import com.sauliyo15.carregistry.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Obtener todos los vehículos", description = "Devuelve una lista de todos los vehículos disponibles.")
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
    @Operation(summary = "Obtener un vehículo por su ID", description = "Devuelve los detalles de un vehículo específico dado su ID.")
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
    @Operation(summary = "Añadir un nuevo vehículo", description = "Permite agregar un nuevo vehículo a la lista proporcionando los detalles del vehículo en el cuerpo de la solicitud.")
    public ResponseEntity<?> addVehicle(@RequestBody VehicleRequest vehicleRequest) {
        log.info("Añadiendo coche");
        try {
            return ResponseEntity.ok(vehicleService.addVehicle(vehicleRequest));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
        }
    }

    @PutMapping("/vehicles/{id}")
    @Operation(summary = "Actualizar un vehículo", description = "Actualiza los detalles de un vehículo existente dado su ID.")
    public ResponseEntity<?> updateVehicle(@PathVariable Integer id, @RequestBody VehicleRequest vehicleRequest) {
        log.info("Modificando coche");
        try {
            return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleRequest));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
        }
    }

    @DeleteMapping("/vehicles/{id}")
    @Operation(summary = "Eliminar un vehículo", description = "Elimina un vehículo de la lista dado su ID.")
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

