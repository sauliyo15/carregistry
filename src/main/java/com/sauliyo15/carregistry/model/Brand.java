package com.sauliyo15.carregistry.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    private int id;
    private String name;
    private int warranty;
    private String country;
}
