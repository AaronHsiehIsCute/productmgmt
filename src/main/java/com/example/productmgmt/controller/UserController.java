package com.example.productmgmt.controller;

import com.example.productmgmt.dto.CreateUserRequest;
import com.example.productmgmt.entity.Product;
import com.example.productmgmt.entity.User;
import com.example.productmgmt.mapper.UserMapper;
import com.example.productmgmt.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userMapper.findByUsername(username);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('STAFF')")
    public String createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return "User created successfully.";
    }
}
