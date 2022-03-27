package com.company.enums;

public enum RoleEnums {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_DIRECTOR("ROLE_DIRECTOR"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_USER("ROLE_USER");
    private final String roleName;

    RoleEnums(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
