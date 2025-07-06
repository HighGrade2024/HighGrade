package com.example.highgrade.dto.auth;

import com.example.highgrade.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterMemberRequestDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    @Builder
    public RegisterMemberRequestDto(String email,
                                    String name,
                                    String phoneNumber,
                                    String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .password(password)
            .name(name)
            .phoneNumber(phoneNumber)
            .build();
    }
}
