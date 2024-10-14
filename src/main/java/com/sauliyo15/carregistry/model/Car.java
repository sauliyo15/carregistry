package com.sauliyo15.carregistry.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

    private String brand;
    private String model;
    private int year;
}

