package com.sauliyo15.carregistry.exception;

public class CarsNotFoundException extends RuntimeException {

    public CarsNotFoundException() {
        super("No cars found");
    }
}