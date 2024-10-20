package com.sauliyo15.carregistry.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    private Integer id;
    private String brand;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fuelType;
    private Integer numDoors;
}

