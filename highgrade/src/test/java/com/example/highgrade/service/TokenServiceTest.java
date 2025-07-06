package com.example.highgrade.service;

import com.example.highgrade.config.security.TokenProvider;
import com.example.highgrade.dto.token.TokenResponseDto;
import com.example.highgrade.entity.Member;
import com.example.highgrade.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private RedissonClient redissonClient;

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        // given
        Member member = Member.builder().email("test@test.com").role(Role.MEMBER).build();
        when(tokenProvider.createAccessToken(any(Authentication.class))).thenReturn("accessToken");
        when(tokenProvider.createRefreshToken(any(Authentication.class))).thenReturn("refreshToken");
        RBucket rBucket = mock(RBucket.class);
        when(redissonClient.getBucket(any(String.class))).thenReturn(rBucket);

        // when
        ResponseEntity<TokenResponseDto> response = tokenService.login(member);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getAccessToken()).isEqualTo("accessToken");
    }
}
