package com.company.resource;

import lombok.Data;

import java.io.Serializable;

@Data
public class JWTAuthResponse implements Serializable {
    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
