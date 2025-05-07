package com.example.productmgmt.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    private Long id;

    private String name;

    private String description;
}
