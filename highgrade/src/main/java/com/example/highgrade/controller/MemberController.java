package com.example.highgrade.controller;

import com.example.highgrade.dto.RegisterMemberDto;
import com.example.highgrade.dto.SignInMemberDto;
import com.example.highgrade.dto.SignUpMemberDto;
import com.example.highgrade.entity.Members;
import com.example.highgrade.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/member")
    public ResponseEntity<Members> saveMember(@RequestBody RegisterMemberDto dto){
        Members savedMember = memberService.saveMember(dto);
        return ResponseEntity.ok(savedMember);
    }

    @PostMapping(value = "/auth/signIn")
    public ResponseEntity<?> signInMember(@RequestBody SignInMemberDto dto){
        return ResponseEntity.ok(memberService.signInMember(dto));
    }

    @PostMapping(value = "/auth/signUp")
    public ResponseEntity<Members> signUpMember(@RequestBody SignUpMemberDto dto){
        memberService.signUpMember(dto);
        Members signUpMember = dto.toEntity();
        return ResponseEntity.ok(signUpMember);
    }
}
