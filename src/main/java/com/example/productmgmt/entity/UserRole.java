package com.example.productmgmt.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
    private Long userId;
    private Long roleId;
}
