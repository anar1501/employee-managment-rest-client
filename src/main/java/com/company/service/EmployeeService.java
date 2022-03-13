package com.company.service;

import com.company.data.dto.request.EmployeeRequestDto;
import com.company.data.dto.response.EmployeeResponseDto;
import com.company.data.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDto> getAll();
//    EmployeeResponseDto getById(Long id);
    void save(EmployeeRequestDto requestDto);
}
