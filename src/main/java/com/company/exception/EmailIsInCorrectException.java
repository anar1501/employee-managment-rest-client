package com.company.exception;

public class EmailIsInCorrectException extends RuntimeException{
    private String message;

    public EmailIsInCorrectException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
