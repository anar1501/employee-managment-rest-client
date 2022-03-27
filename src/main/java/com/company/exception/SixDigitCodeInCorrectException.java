package com.company.exception;

public class SixDigitCodeInCorrectException extends RuntimeException{
    private final String message;

    public SixDigitCodeInCorrectException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
