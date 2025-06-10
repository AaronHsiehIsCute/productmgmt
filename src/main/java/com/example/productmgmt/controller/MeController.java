package com.example.productmgmt.controller;

import com.example.productmgmt.entity.User;
import com.example.productmgmt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MeController {

    private final UserMapper userMapper;

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userMapper.findByUsername(userDetails.getUsername());
    }
}
