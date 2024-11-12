package com.sauliyo15.carregistry.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private int id;
    private Brand brand;
    private String model;
    private int milleage;
    private double price;
    private int year;
    private String description;
    private String colour;
    private String fuelType;
    private int numDoors;
}

