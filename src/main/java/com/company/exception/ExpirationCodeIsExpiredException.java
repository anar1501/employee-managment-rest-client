package com.company.exception;

public class ExpirationCodeIsExpiredException extends RuntimeException {
    private final String message;

    public ExpirationCodeIsExpiredException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
