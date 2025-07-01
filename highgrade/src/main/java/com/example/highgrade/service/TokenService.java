package com.example.highgrade.service;

import com.example.highgrade.config.security.TokenProvider;
import com.example.highgrade.entity.Members;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RedissonClient redissonClient;
    private final TokenProvider tokenProvider;
    private final static long TOKEN_EXPIRE_SECONDS = 60*60;


    @Transactional
    public String login(final Members foundMember) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            foundMember.getEmail(), null, List.of()
        );
        String token = tokenProvider.createToken(authentication);
        RBucket<String> bucket = redissonClient.getBucket(token);
        bucket.set(authentication.getName(), Duration.ofSeconds(TOKEN_EXPIRE_SECONDS));
        return token;
    }

    @Transactional
    public void logout(String token){
        String email = tokenProvider.getAuthentication(token).getName();
        if (email == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        RBucket<String> bucket = redissonClient.getBucket(email);
        bucket.delete();
    }
}
