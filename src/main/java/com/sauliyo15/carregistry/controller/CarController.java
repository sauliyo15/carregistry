package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.CarRequest;
import com.sauliyo15.carregistry.controller.dtos.CarResponse;
import com.sauliyo15.carregistry.controller.mappers.CarMapper;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;


    @GetMapping("/cars")
    @Operation(summary = "Get all cars", description = "Returns a list of all available cars.")
    public ResponseEntity<?> getCars() {
        log.info("Fetching cars");
        try {
            CompletableFuture<List<Car>> carList = carService.getCars();
            List<CarResponse> carResponseList = carMapper.toCarListResponse(carList.get());
            return ResponseEntity.ok(carResponseList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/cars/{id}")
    @Operation(summary = "Get a car by ID", description = "Returns the details of a specific car given its ID.")
    public ResponseEntity<?> getCarById(@PathVariable Integer id) {
        log.info("Fetching car with ID: {}", id);
        try {
            return ResponseEntity.ok(carMapper.toCarResponse(carService.getCarById(id)));
        } catch (Exception e) {
            log.error("Error fetching car with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/cars")
    @Operation(summary = "Add new car", description = "Allows add new car by providing the details in the body of the request.")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest) {
        log.info("Adding new car with details: {}", carRequest);
        try {
            return ResponseEntity.ok(carMapper.toCarResponse(carService.addCar(carMapper.toCar(carRequest))));
        }
        catch (Exception e) {
            log.error("Error adding car", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/cars/{id}")
    @Operation(summary = "Update a car", description = "Updates the details of an existing car given its ID.")
    public ResponseEntity<?> updateCar(@PathVariable Integer id, @RequestBody CarRequest carRequest) {
        log.info("Updating car with ID: {}", id);
        try {
            return ResponseEntity.ok(carMapper.toCarResponse(carService.updateCar(id, carMapper.toCar(carRequest))));
        } catch (Exception e) {
            log.error("Error updating car with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/cars/{id}")
    @Operation(summary = "Delete a car", description = "Delete a car given its ID.")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id) {
        log.info("Deleting car with ID: {}", id);
        try {
            carService.deleteCar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting car with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

