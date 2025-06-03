package com.example.productmgmt.controller;

import com.example.productmgmt.dto.LoginRequest;
import com.example.productmgmt.dto.TokenResponse;
import com.example.productmgmt.service.RefreshTokenService;
import com.example.productmgmt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        // 產生 Token
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        // 存入 Redis
        long refreshExpire = jwtUtil.getRemainingMillis(refreshToken);
        refreshTokenService.storeRefreshToken(user.getUsername(), refreshToken, refreshExpire);

        // 回傳雙 token
        return new TokenResponse(accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader("Authorization") String refreshHeader) {
        if (refreshHeader == null || !refreshHeader.startsWith("Bearer ")) {
            throw new RuntimeException("缺少 Refresh Token");
        }

        String refreshToken = refreshHeader.substring(7);
        String username = jwtUtil.extractUsername(refreshToken);

        if (!refreshTokenService.isRefreshTokenValid(username, refreshToken)
                || jwtUtil.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh Token 無效或已過期，請重新登入");
        }

        // 重新載入完整的使用者資料（含權限）
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("")         // 無需密碼
                .authorities("")        // 無權限就傳空
                .build();

        String newAccessToken = jwtUtil.generateAccessToken(userDetails);
        return new TokenResponse(newAccessToken, refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("缺少 Refresh Token");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtUtil.extractUsername(refreshToken);

        // 刪除 Redis 中的 refresh token
        refreshTokenService.deleteRefreshToken(username);

        return ResponseEntity.ok("已成功登出");
    }


}
