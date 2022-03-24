package com.company.exception;

public class SixDigitCodeInCorrectException extends RuntimeException{
    private String message;

    public SixDigitCodeInCorrectException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
