package com.company.data.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleResponseDto implements Serializable {
    private Long roleId;
    private String name;
}
