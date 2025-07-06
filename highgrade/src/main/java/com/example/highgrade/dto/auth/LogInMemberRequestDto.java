package com.example.highgrade.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogInMemberRequestDto {
    private String email;
    private String password;

    @Builder
    public LogInMemberRequestDto(String email,
                                 String password) {
        this.email = email;
        this.password = password;
    }
}
