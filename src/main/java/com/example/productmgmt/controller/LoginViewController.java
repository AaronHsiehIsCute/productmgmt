package com.example.productmgmt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginViewController {

    @GetMapping("/login/success/{token}")
    public String loginSuccessPage(@PathVariable String token, Model model) {
        model.addAttribute("token", token);
        // 對應到 src/main/resources/templates/success.html
        return "success";
    }
}
