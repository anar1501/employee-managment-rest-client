package com.company.data.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeRequestDto {
    private String name;
    private String surname;
    private int age;
    private BigDecimal salary;
}
