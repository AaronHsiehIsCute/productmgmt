package com.example.productmgmt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        String rawPassword = "123456";
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
        System.out.println("加密後密碼：" + encodedPassword);
    }
}
