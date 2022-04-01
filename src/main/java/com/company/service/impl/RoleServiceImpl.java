package com.company.service.impl;

import com.company.config.ModelMapperConfiguration;
import com.company.data.dto.response.RoleResponseDto;
import com.company.data.repository.RoleRepository;
import com.company.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleResponseDto> findAll() {
        return roleRepository.findAll().stream().map((role) -> ModelMapperConfiguration.map(role, RoleResponseDto.class)).collect(Collectors.toList());
    }

}
