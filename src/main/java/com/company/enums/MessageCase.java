package com.company.enums;

public enum MessageCase {
    NOT_FOUND("Such Employee not found!"),
    EMPLOYEE_CREATED("Employee successfully created"),
    USER_NOT_FOUND("Username incorrect!"),
    SUCCESSFULLY_LOGIN("User successfully login"),
    SUCCESSFULLY_REGISTER("User successfully registered"),
    EMAIL_ALREADY_TAKEN("Such email already registered"),
    USERNAME_ALREADY_TAKEN("Such username already registered"),
    USER_UNCONFIRMED("User unconfirmed!!"),
    WRONG_PASSWORD("Password incorrect"),
    EXPIRATION_TIME_IS_EXPIRED("Your Confirmed link of Expiration Time is Expired!!"),
    REGISTRATION_SUCCESSFULLY_CONFIRMED("Your Registration successfully Confirmed"),
    USER_ALREADY_CONFIRMED("Your Confirmation code already confirmed!!"),
    RESEND_EMAIL_SUCCESSFULLY_SENT("Your Confirmation code successfully sent your email"),
    EMAIL_IS_INCORRECT("Email is incorrect!"),
    PASSWORD_CONFIRMATION_LINK("Your forget password code successfully sent your email"),
    PASSWORD_SUCCESSFULLY_CHANGED("Your new password successfully changed"),
    SIX_DIGIT_CODE("6 digit code is wrong"),
    EMPLOYEE_UPDATED("Employee successfully updated!"),
    EMPLOYEE_DELETED("Employee successfully deleted!"),
    EMPLOYEE_DOES_NOT_EXISTS("You don't have any authorization process of resource!"),
    USER_ROLE_SUCESSFULLY_UPDATED("Role sucessfully updated");

    private final String message;

    MessageCase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
