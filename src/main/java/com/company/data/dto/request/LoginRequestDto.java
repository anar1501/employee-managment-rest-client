package com.company.data.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequestDto implements Serializable {
    private String usernameOrEmail;
    private String password;
}
