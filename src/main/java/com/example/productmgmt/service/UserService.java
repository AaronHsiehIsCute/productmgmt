// src/main/java/com/example/productmgmt/service/UserService.java
package com.example.productmgmt.service;

import com.example.productmgmt.dto.CreateUserRequest;
import com.example.productmgmt.entity.User;
import com.example.productmgmt.entity.UserStatus;
import com.example.productmgmt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request) {
        User existing = userMapper.findByUsername(request.getUsername());
        if (existing != null) {
            throw new RuntimeException("帳號已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE); // Enum：ACTIVE or INACTIVE
        user.setCreatedAt(LocalDateTime.now());

        userMapper.insertUser(user);
    }
}
