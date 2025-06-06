package com.example.productmgmt.service;

import com.example.productmgmt.dto.CreateUserRequest;
import com.example.productmgmt.entity.User;
import com.example.productmgmt.entity.UserStatus;
import com.example.productmgmt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request) {
        User existing = userMapper.findByUsername(request.getUsername());
        if (existing != null) {
            throw new RuntimeException("帳號已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        userMapper.insertUser(user);
    }

    public User findOrCreateUserByEmail(String email) {
        return userMapper.findByEmail(email)
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername(email.split("@")[0]);
                    user.setEmail(email);
                    user.setStatus(UserStatus.ACTIVE);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

                    userMapper.insert(user);
                    return user;
                });
    }
}
