package com.example.highgrade.entity;

import com.example.highgrade.dto.RegisterMemberDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String phoneNumber;
    private String oauthid;
    @NotNull
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotNull
    private String password;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Member(String name,
                  String email,
                  String phoneNumber,
                  String password) {
        this.name = name;
        this.email = email;
        this.role = Role.MEMBER;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public RegisterMemberDto toDto() {
        return RegisterMemberDto.builder()
            .email(email)
            .password(password)
            .name(name)
            .phoneNumber(phoneNumber)
            .build();
    }
}
