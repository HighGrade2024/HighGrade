package com.example.highgrade.controller;

import com.example.highgrade.dto.*;
import com.example.highgrade.entity.Member;
import com.example.highgrade.service.MemberService;
import com.example.highgrade.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class MemberController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @PostMapping(value = "/member")
    public ResponseEntity<Member> saveMember(@RequestBody RegisterMemberDto dto){
        Member savedMember = memberService.saveMember(dto);
        return ResponseEntity.ok(savedMember);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDto> loginMember(@RequestBody SignInMemberDto dto){
        return memberService.loginMember(dto);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Member> signUpMember(@RequestBody SignUpMemberDto dto){
        memberService.signUpMember(dto);
        Member signUpMember = dto.toEntity();
        return ResponseEntity.ok(signUpMember);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logoutMember(HttpServletRequest request){
        return tokenService.logout(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(
        @CookieValue(value = "refreshToken", required = false) String refreshToken){
        return tokenService.refresh(refreshToken);
    }
}
