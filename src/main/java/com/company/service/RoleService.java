package com.company.service;

import com.company.data.dto.response.PermissionResponseDto;
import com.company.data.dto.response.RoleResponseDto;

import java.util.List;

public interface RoleService {
    List<RoleResponseDto> findAll();
    List<PermissionResponseDto>findPermissionsOfRole(Long roleId);
}
