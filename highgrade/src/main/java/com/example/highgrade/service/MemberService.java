package com.example.highgrade.service;

import com.example.highgrade.dto.RegisterMemberDto;
import com.example.highgrade.dto.SignInMemberDto;
import com.example.highgrade.dto.SignUpMemberDto;
import com.example.highgrade.entity.Members;
import com.example.highgrade.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Members saveMember(RegisterMemberDto dto){
        Members member = dto.toEntity();
        return memberRepository.save(member);
    }

    @Transactional
    public Members findMember(Long id){
        return memberRepository.findById(id).orElseThrow(
            NoSuchElementException::new
        );
    }

    @Transactional
    public void signInMember(SignInMemberDto dto){
        Members foundMember = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
            NoSuchElementException::new
        );
        if(!passwordEncoder.matches(dto.getPassword(), foundMember.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void signUpMember(SignUpMemberDto dto){
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Members foundMember = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
            NoSuchElementException::new
        );
        Members newMemeber = Members.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .password(encodedPassword)
            .phoneNumber(dto.getPhoneNumber())
            .build();
        memberRepository.save(newMemeber);
    }
}
