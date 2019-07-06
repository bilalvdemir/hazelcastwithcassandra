package com.example.bilal.exception;

public class AlreadyExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public AlreadyExistException(String exceptionString) {
        super(exceptionString);
    }

}
