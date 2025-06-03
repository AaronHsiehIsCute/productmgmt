package com.example.productmgmt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;
    private final String PREFIX = "refresh:";

    public void storeRefreshToken(String username, String refreshToken, long expirationMillis) {
        redisTemplate.opsForValue().set(
                PREFIX + username,
                refreshToken,
                Duration.ofMillis(expirationMillis)
        );
    }

    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(PREFIX + username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(PREFIX + username);
    }

    public boolean isRefreshTokenValid(String username, String incomingToken) {
        String storedToken = getRefreshToken(username);
        return storedToken != null && storedToken.equals(incomingToken);
    }
    
}
