package com.sauliyo15.carregistry.exception;

public class BrandNotFoundException extends RuntimeException {

    public BrandNotFoundException(Integer id) {
        super("Brand not found with ID: " + id);
    }

    public BrandNotFoundException(String name) {
        super("Brand not found with NAME: " + name);
    }
}
