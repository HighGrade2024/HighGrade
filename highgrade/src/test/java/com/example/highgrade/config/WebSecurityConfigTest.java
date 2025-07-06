package com.example.highgrade.config;

import com.example.highgrade.config.security.JwtAccessDeniedHandler;
import com.example.highgrade.config.security.JwtAuthenticationEntryPoint;
import com.example.highgrade.config.security.JwtFilter;
import com.example.highgrade.config.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebSecurityConfigTest {

    @InjectMocks
    private WebSecurityConfig webSecurityConfig;

    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Mock
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("PasswordEncoder Bean이 BCryptPasswordEncoder를 반환하는지 테스트")
    void getPasswordEncoder() {
        // when
        PasswordEncoder passwordEncoder = webSecurityConfig.getPasswordEncoder();

        // then
        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    @DisplayName("AuthenticationManager Bean 생성 테스트")
    void authenticationManager() throws Exception {
        // given
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        // when
        AuthenticationManager result = webSecurityConfig.authenticationManager(authenticationConfiguration);

        // then
        assertThat(result).isEqualTo(authenticationManager);
    }
}
