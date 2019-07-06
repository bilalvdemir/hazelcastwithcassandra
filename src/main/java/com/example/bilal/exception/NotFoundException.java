package com.example.bilal.exception;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String exceptionString) {
        super(exceptionString);
    }
}
