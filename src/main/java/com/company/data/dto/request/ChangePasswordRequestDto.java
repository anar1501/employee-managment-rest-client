package com.company.data.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePasswordRequestDto implements Serializable {
    private String newPassword;
}
