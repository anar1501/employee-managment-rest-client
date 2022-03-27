package com.company.data.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDto implements Serializable {
    private String username;
    private String password;
    private String email;
}
