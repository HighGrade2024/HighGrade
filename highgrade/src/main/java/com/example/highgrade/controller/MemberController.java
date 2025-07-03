package com.example.highgrade.controller;

import com.example.highgrade.dto.*;
import com.example.highgrade.entity.MemberDetail;
import com.example.highgrade.entity.Members;
import com.example.highgrade.config.security.JwtFilter;
import com.example.highgrade.service.MemberService;
import com.example.highgrade.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @PostMapping(value = "/member")
    public ResponseEntity<Members> saveMember(@RequestBody RegisterMemberDto dto){
        Members savedMember = memberService.saveMember(dto);
        return ResponseEntity.ok(savedMember);
    }

    @PostMapping(value = "/auth/login")
    public ResponseEntity<TokenResponseDto> loginMember(@RequestBody SignInMemberDto dto){
        return memberService.loginMember(dto);
    }

    @PostMapping(value = "/auth/signup")
    public ResponseEntity<Members> signUpMember(@RequestBody SignUpMemberDto dto){
        memberService.signUpMember(dto);
        Members signUpMember = dto.toEntity();
        return ResponseEntity.ok(signUpMember);
    }

    @PostMapping(value = "/auth/logout")
    public ResponseEntity<Void> logoutMember(HttpServletRequest request){
        return tokenService.logout(request);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<TokenResponseDto> refresh(
        @CookieValue(value = "refreshToken", required = false) String refreshToken){
        return tokenService.refresh(refreshToken);
    }
}
