package com.company.service.impl;

import com.company.config.ModelMapperConfiguration;
import com.company.data.dto.response.PermissionResponseDto;
import com.company.data.dto.response.RoleResponseDto;
import com.company.data.entity.Permission;
import com.company.data.entity.Role;
import com.company.data.repository.RoleRepository;
import com.company.service.RoleService;
import com.company.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.company.utils.StringUtils.parseString;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public List<RoleResponseDto> findAll() {
        return roleRepository.findAll().stream().map((role) -> ModelMapperConfiguration.map(role, RoleResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PermissionResponseDto> findPermissionsOfRole(Long roleId) {
        try {
            return roleRepository.findRoleByRoleId(roleId).getPermissions().stream().map((permission) -> ModelMapperConfiguration.map(permission, PermissionResponseDto.class)).collect(Collectors.toList());
        } catch (Exception exception) {
            LOG.error(parseString(exception));
        }
        return new ArrayList<>();
    }

}
