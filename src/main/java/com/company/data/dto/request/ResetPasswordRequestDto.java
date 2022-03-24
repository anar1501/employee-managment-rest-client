package com.company.data.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    private String sixDigitCode;
    private String newPassword;
}
