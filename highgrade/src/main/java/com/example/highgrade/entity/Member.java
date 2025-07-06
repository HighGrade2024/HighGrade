package com.example.highgrade.entity;

import com.example.highgrade.dto.auth.MemberResponseDto;
import com.example.highgrade.dto.auth.RegisterMemberRequestDto;
import jakarta.persistence.*;
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
    @Column(nullable = false)
    private String name;
    private String phoneNumber;
    private String oauthid;
    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String password;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Member(
        Long id,
        String name,
                  String email,
                  String phoneNumber,
                  String password,
                  Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public RegisterMemberRequestDto toDto() {
        return RegisterMemberRequestDto.builder()
            .email(email)
            .password(password)
            .name(name)
            .phoneNumber(phoneNumber)
            .build();
    }
}
