package com.company.data.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EmployeeResponseDto implements Serializable {
    private String name;
    private String surname;
    private int age;
    private BigDecimal salary;
}
