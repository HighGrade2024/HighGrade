package com.example.highgrade.service;

import com.example.highgrade.config.security.TokenProvider;
import com.example.highgrade.entity.Members;
import com.example.highgrade.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RedissonClient redissonClient;
    private final TokenProvider tokenProvider;
    private final static long TOKEN_EXPIRE_SECONDS = 60*60;
    private final static long REFRESH_TOKEN_EXPIRE_SECONDS = 60*60*24;
    private final static String REFRESH_TOKEN_PREFIX = "refreshToken:";
    private final static String ACCESS_TOKEN_PREFIX = "accessToken:";

    @Transactional
    public String login(final Members foundMember) {
        String email = foundMember.getEmail();
        String role = foundMember.getRole().name();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            email, null,
            List.of(new SimpleGrantedAuthority(role))
        );

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = UUID.randomUUID().toString();

        RBucket<String> bucket = redissonClient.getBucket(ACCESS_TOKEN_PREFIX+foundMember.getEmail());
        bucket.set(accessToken, Duration.ofSeconds(TOKEN_EXPIRE_SECONDS));

        saveRefreshToken(email, refreshToken);
        return accessToken;
    }

    @Transactional
    public ResponseEntity<Void> logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String token = authHeader.substring(7);
        String email = tokenProvider.getAuthentication(token).getName();
        redissonClient.getBucket(ACCESS_TOKEN_PREFIX+email).delete();
        deleteRefreshToken(email);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public void saveRefreshToken(String email, String refreshToken){
        RBucket<String> bucket = redissonClient.getBucket(REFRESH_TOKEN_PREFIX+email);
        bucket.set(refreshToken, Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_SECONDS));
    }

    @Transactional
    public String getRefreshToken(String email) {
        RBucket<String> bucket = redissonClient.getBucket(REFRESH_TOKEN_PREFIX + email);
        return bucket.get();
    }

    @Transactional
    public void deleteRefreshToken(String email) {
        RBucket<String> bucket = redissonClient.getBucket(REFRESH_TOKEN_PREFIX + email);
        bucket.delete();
    }
}
