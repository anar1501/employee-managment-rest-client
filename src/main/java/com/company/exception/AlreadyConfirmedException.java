package com.company.exception;

public class AlreadyConfirmedException extends RuntimeException {
    private final String message;

    public AlreadyConfirmedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
