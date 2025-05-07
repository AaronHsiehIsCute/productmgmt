package com.example.productmgmt.controller;

import com.example.productmgmt.entity.User;
import com.example.productmgmt.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userMapper.findByUsername(username);
    }
}
