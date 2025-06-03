// src/main/java/com/example/productmgmt/dto/CreateUserRequest.java
package com.example.productmgmt.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
}
