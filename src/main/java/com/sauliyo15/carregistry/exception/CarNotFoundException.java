package com.sauliyo15.carregistry.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Integer id) {
        super("Car not found with ID: " + id);
    }
}
