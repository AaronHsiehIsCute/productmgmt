package com.example.productmgmt.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission {
    private Long roleId;
    private Long permissionId;
}
