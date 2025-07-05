package com.example.highgrade.dto;

import com.example.highgrade.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogInMemberDto {
    private String email;
    private String password;

    @Builder
    public LogInMemberDto(String email,
                          String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .password(password)
            .build();
    }
}
