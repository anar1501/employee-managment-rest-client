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
    RESEND_EMAIL_SUCCESSFULLY_SENT("Your Confirmation link successfully sended your email"),
    EMAIL_IS_INCORRECT("Email is incorrect!"),
    PASSWORD_CONFIRMATION_LINK("Your forget password confirmation link successfully sent"),
    PASSWORD_SUCCESSFULLY_CHANGED("Your new password successfully changed");

    private String message;

    MessageCase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
