package com.company.data.repository;

import com.company.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String roleName);
    Role findRoleByRoleId(Long roleId);
}
