package com.example.highgrade.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로
            .allowedOrigins("*")  // 모든 Origin 허용
            .allowedMethods("*")  // GET, POST, PUT, DELETE 등 모두 허용
            .allowedHeaders("*")  // 모든 헤더 허용
            .allowCredentials(false);  // 쿠키 인증 안 할 거면 false
    }
}
