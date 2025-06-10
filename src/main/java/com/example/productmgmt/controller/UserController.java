package com.example.productmgmt.controller;

import com.example.productmgmt.dto.CreateUserRequest;
import com.example.productmgmt.entity.Product;
import com.example.productmgmt.entity.User;
import com.example.productmgmt.mapper.UserMapper;
import com.example.productmgmt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "用戶管理 API")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Operation(summary = "尋找用戶")
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userMapper.findByUsername(username);
    }

    @Operation(summary = "創建用戶")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('STAFF')")
    public String createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return "User created successfully.";
    }
}
