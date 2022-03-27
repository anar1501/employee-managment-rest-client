package com.company.service;

import com.company.data.dto.request.EmployeeRequestDto;
import com.company.data.dto.response.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDto> getAll();
    void save(EmployeeRequestDto requestDto);
    void updateEmployee(Long id, EmployeeRequestDto requestDto);
    void deleteEmployee(Long id);
}
