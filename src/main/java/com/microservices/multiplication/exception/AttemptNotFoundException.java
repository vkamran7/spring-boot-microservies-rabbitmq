package com.microservices.multiplication.exception;

public class AttemptNotFoundException extends RuntimeException {
    private String message;

    public AttemptNotFoundException(String message) {
        super(message);
    }
}
