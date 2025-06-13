package com.example.productmgmt.security.handler;

import com.example.productmgmt.entity.User;
import com.example.productmgmt.service.JwtService;
import com.example.productmgmt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userService.findOrCreateUserByEmail(email);
        String token = jwtService.generateToken(user);

        // 將 token 存入 session（或 HttpOnly Cookie 更安全）
        request.getSession().setAttribute("JWT_TOKEN", token);

        // 導向前端的成功頁面
        response.sendRedirect("/login/success/" + token);
    }

}