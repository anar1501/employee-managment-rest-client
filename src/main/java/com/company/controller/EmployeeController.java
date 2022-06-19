package com.company.controller;

import com.company.data.dto.request.EmployeeRequestDto;
import com.company.data.dto.response.EmployeeResponseDto;
import com.company.enums.MessageCase;
import com.company.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.company.enums.MessageCase.*;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('write')")
    @PostMapping
    public ResponseEntity<String> save(@RequestBody EmployeeRequestDto requestDto) {
        employeeService.save(requestDto);
        return new ResponseEntity<>(EMPLOYEE_CREATED.getMessage(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR')")
    @PutMapping(path = "{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDto requestDto) {
        employeeService.updateEmployee(id, requestDto);
        return ResponseEntity.ok(EMPLOYEE_UPDATED.getMessage());
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DIRECTOR','ROLE_MANAGER')")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(EMPLOYEE_DELETED.getMessage());
    }

//    @GetMapping(value = "search-employee/{id}")
//    public ResponseEntity<String> searchEmployee(@PathVariable("id") Long id) {
//
//    }

}
