package com.sauliyo15.carregistry.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Brand {

    private int id;
    private String name;
    private int warranty;
    private String country;
}
