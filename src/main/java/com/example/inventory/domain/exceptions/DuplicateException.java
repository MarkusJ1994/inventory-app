package com.example.inventory.domain.exceptions;

public class DuplicateException extends RuntimeException {

    public DuplicateException(String msg) {
        super(msg);
    }

}
