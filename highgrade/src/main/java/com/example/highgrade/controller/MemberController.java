package com.example.highgrade.controller;

import com.example.highgrade.config.security.TokenProvider;
import com.example.highgrade.dto.*;
import com.example.highgrade.dto.auth.LogInMemberRequestDto;
import com.example.highgrade.dto.auth.RegisterMemberRequestDto;
import com.example.highgrade.dto.token.TokenResponseDto;
import com.example.highgrade.entity.Member;
import com.example.highgrade.service.JoinService;
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
    private final JoinService joinService;
    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final static String GRANT_TYPE = "Bearer ";
    private final static String AUTH_HEADER = "Authorization";

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDto> loginMember(@RequestBody LogInMemberRequestDto dto){
        return memberService.loginMember(dto);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Member> registerMember(@RequestBody RegisterMemberRequestDto dto){
        return ResponseEntity.ok().body(memberService.registerMember(dto));
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



    @PostMapping("/check")
    public void check(final HttpServletRequest request){
        String authHeader = request.getHeader(AUTH_HEADER);
        System.out.println("authheader is " + authHeader);
        String token = authHeader.substring(7);
        String email = tokenProvider.getAuthentication(token).getName();
        System.out.println("email is " + email);
        System.out.println(tokenProvider.getAuthentication(token).toString());
        tokenProvider.validateToken(token);
    }
}
