package com.example.productmgmt.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Role> roles;

    public enum Status {
        ACTIVE, INACTIVE
    }
}
