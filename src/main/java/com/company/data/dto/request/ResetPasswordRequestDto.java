package com.company.data.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPasswordRequestDto implements Serializable {
    private String sixDigitCode;
    private String newPassword;
}
