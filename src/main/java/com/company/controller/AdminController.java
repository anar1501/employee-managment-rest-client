package com.company.controller;

import com.company.data.dto.response.PermissionResponseDto;
import com.company.data.dto.response.RoleResponseDto;
import com.company.data.dto.response.UserResponseDto;
import com.company.service.RoleService;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.company.enums.MessageCase.USER_ROLE_SUCESSFULLY_UPDATED;

@RestController
@RequestMapping(value = "api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    //    @PreAuthorize("hasAuthority('read')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> showUsersExceptAdmin() {
        return ResponseEntity.ok(userService.showUsersExpectAdmin());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponseDto> findUserRoleById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.findUserRole(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "{id}/{roleId}")
    public ResponseEntity<String> updateUserRoleUserAndRoleId(@PathVariable(value = "id") Long id, @PathVariable(value = "roleId") Long roleId) {
        userService.updateUserRoleByUserAndRoleId(id, roleId);
        return new ResponseEntity<>(USER_ROLE_SUCESSFULLY_UPDATED.getMessage(), HttpStatus.OK);
    }

    @GetMapping(value = "roles")
    public ResponseEntity<List<RoleResponseDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping(value = "role/{id}/permissions")
    public ResponseEntity<List<PermissionResponseDto>> findPermissionsOfRole(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(roleService.findPermissionsOfRole(id));
    }

}
