package com.example.productmgmt.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    private Long id;

    private String name;

    private String description;
}
