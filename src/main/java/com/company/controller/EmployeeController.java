package com.company.controller;

import com.company.data.dto.request.EmployeeRequestDto;
import com.company.data.dto.response.EmployeeResponseDto;
import com.company.enums.MessageCase;
import com.company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.company.enums.MessageCase.EMPLOYEE_CREATED;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

//    @GetMapping(path = "{id}")
//    public ResponseEntity<EmployeeResponseDto> getById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(employeeService.getById(id));
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<String> save(@RequestBody EmployeeRequestDto requestDto) {
        employeeService.save(requestDto);
        return new ResponseEntity<>(EMPLOYEE_CREATED.getMessage(), HttpStatus.CREATED);
    }
}
