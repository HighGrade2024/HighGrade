package com.example.highgrade.dto.token;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {
    private String message;
    private String grantType;
    private String accessToken;
}
