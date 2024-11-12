package com.sauliyo15.carregistry.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {

    private int id;
    private String name;
    private int warranty;
    private String country;
}
