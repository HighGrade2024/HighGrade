package com.example.highgrade.service;

import com.example.highgrade.config.security.TokenProvider;
import com.example.highgrade.dto.TokenResponseDto;
import com.example.highgrade.entity.Members;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RedissonClient redissonClient;
    private final TokenProvider tokenProvider;
    private final static long TOKEN_EXPIRE_SECONDS = 60*60;
    private final static long REFRESH_TOKEN_EXPIRE_SECONDS = 60*60*24;
    private final static String REFRESH_TOKEN_PREFIX = "refreshToken:";
    private final static String ACCESS_TOKEN_PREFIX = "accessToken:";
    private final static String GRANT_TYPE = "Bearer ";
    private final static String AUTH_HEADER = "Authorization";

    @Transactional
    public ResponseEntity<TokenResponseDto> login(final Members foundMember) {
        String email = foundMember.getEmail();
        String role = foundMember.getRole().name();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            email, null,
            List.of(new SimpleGrantedAuthority(role))
        );

        String accessToken = tokenProvider.createAccessToken(authentication);
        return getTokenResponseDtoResponseEntity(accessToken, email, authentication);
    }

    @Transactional
    public ResponseEntity<Void> logout(HttpServletRequest request){
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(GRANT_TYPE)) {
            return ResponseEntity.badRequest().build();
        }
        String token = authHeader.substring(7);
        String email = tokenProvider.getAuthentication(token).getName();
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

    @Transactional
    public ResponseEntity<TokenResponseDto> refresh(final String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(TokenResponseDto.builder()
                    .grantType(GRANT_TYPE)
                    .message("Refresh token is missing")
                    .build());
        }
        return validateRefreshToken(refreshToken);
    }

    private ResponseEntity<TokenResponseDto> validateRefreshToken(final String refreshToken){
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        String accessToken = authToAccessToken(authentication);
        String email = authentication.getName();
        String oldRefreshToken = redissonClient.getBucket(REFRESH_TOKEN_PREFIX+email).get().toString();
        if(!refreshToken.equals(oldRefreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TokenResponseDto.builder()
                    .message("유효하지 않은 RefreshToken입니다.")
                    .build());
        }
        return getTokenResponseDtoResponseEntity(accessToken, email, authentication);
    }

    private ResponseEntity<TokenResponseDto> getTokenResponseDtoResponseEntity(final String accessToken,
                                                                               final String email,
                                                                               Authentication authentication) {
        String newRefreshToken = authToRefreshToken(authentication);
        saveRefreshToken(email, newRefreshToken);
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", newRefreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")  // 쿠키가 어디 경로에 붙을지
            .maxAge(Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_SECONDS))
            .sameSite("Strict")
            .build();
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken).build());
    }

    private String authToAccessToken(Authentication authentication){
        return tokenProvider.createAccessToken(authentication);
    }

    private String authToRefreshToken(Authentication authentication){
        return tokenProvider.createRefreshToken(authentication);
    }
}
