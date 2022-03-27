package com.company.data.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterConfirmRequestDto implements Serializable {
    private String sixDigitCode;
}
