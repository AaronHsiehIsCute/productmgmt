package com.example.productmgmt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final StringRedisTemplate redisTemplate;

    private final String BLACKLIST_PREFIX = "blacklist:";

    public void blacklistToken(String token, long expirationMillis) {
        System.out.println("🔒 加入黑名單: " + token);
        System.out.println("⏳ 剩餘時間(ms): " + expirationMillis);

        redisTemplate.opsForValue().set(
                BLACKLIST_PREFIX + token,
                "true",
                Duration.ofMillis(expirationMillis)
        );
    }


    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}
