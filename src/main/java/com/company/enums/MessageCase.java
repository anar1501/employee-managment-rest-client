package com.company.enums;

public enum MessageCase {
    NOT_FOUND("Such Employee not found!"),
    EMPLOYEE_CREATED("Employee successfully created"),
    USER_NOT_FOUND("Such User not found!"),
    SUCCESSFULLY_LOGIN("User successfully login"),
    SUCCESSFULLY_REGISTER("User successfully registered"),
    EMAIL_ALREADY_TAKEN("Such email already registered"),
    USERNAME_ALREADY_TAKEN("Such username already registered");
    private String message;

    MessageCase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
