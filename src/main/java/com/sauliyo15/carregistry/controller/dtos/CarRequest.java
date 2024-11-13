package com.sauliyo15.carregistry.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    private String brand;
    private String model;
    private int milleage;
    private double price;
    private int year;
    private String description;
    private String colour;
    private String fuelType;
    private int numDoors;
}

