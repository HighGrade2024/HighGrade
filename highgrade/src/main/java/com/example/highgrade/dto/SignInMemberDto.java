package com.example.highgrade.dto;

import com.example.highgrade.entity.Members;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInMemberDto {
    private String email;
    private String password;

    @Builder
    public SignInMemberDto(String email,
                             String password) {
        this.email = email;
        this.password = password;
    }

    public Members toEntity() {
        return Members.builder()
            .email(email)
            .password(password)
            .build();
    }
}
