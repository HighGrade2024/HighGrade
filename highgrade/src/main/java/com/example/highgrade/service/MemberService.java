package com.example.highgrade.service;

import com.example.highgrade.dto.RegisterMemberDto;
import com.example.highgrade.dto.LogInMemberDto;
import com.example.highgrade.dto.TokenResponseDto;
import com.example.highgrade.entity.Member;
import com.example.highgrade.repository.MemberRepository;
import com.example.highgrade.config.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Transactional
    public Member findMember(Long id){
        return memberRepository.findById(id).orElseThrow(
            NoSuchElementException::new
        );
    }

    @Transactional
    public ResponseEntity<TokenResponseDto> loginMember(LogInMemberDto dto){
        Member foundMember = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
            NoSuchElementException::new
        );
        if(!passwordEncoder.matches(dto.getPassword(), foundMember.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return tokenService.login(foundMember);
    }

    @Transactional
    public void registerMember(RegisterMemberDto dto){
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        try{
            Member foundMember = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
                NoSuchElementException::new
            );
        } catch (NoSuchElementException e){
            Member newMemeber = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(encodedPassword)
                .phoneNumber(dto.getPhoneNumber())
                .build();
            memberRepository.save(newMemeber);
        }
    }
}
