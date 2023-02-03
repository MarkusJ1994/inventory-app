package com.example.inventory.domain.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}
