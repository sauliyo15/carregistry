package com.sauliyo15.carregistry.exception;

public class BrandsNotFoundException extends RuntimeException {

    public BrandsNotFoundException() {
        super("No brands found");
    }
}
