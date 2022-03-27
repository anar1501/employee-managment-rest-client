package com.company.service.impl;

import com.company.config.ModelMapperConfiguration;
import com.company.data.dto.request.EmployeeRequestDto;
import com.company.data.dto.response.EmployeeResponseDto;
import com.company.data.entity.Employee;
import com.company.data.repository.EmployeeRepository;
import com.company.exception.EmployeeNotFoundException;
import com.company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.company.enums.MessageCase.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponseDto> getAll() {
        return employeeRepository.findAll().stream().map(employee -> ModelMapperConfiguration.map(employee, EmployeeResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public void save(EmployeeRequestDto requestDto) {
        employeeRepository.save(ModelMapperConfiguration.map(requestDto, Employee.class));
    }

    @Override
    public void updateEmployee(Long id, EmployeeRequestDto requestDto) {
        Employee updateEmployee = ModelMapperConfiguration.map(requestDto, employeeRepository.findEmployeeByEmployeeId(id));
        updateEmployee.setUpdateDate(new Date());
        employeeRepository.save(updateEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
