package com.example.highgrade.controller;

import com.example.highgrade.dto.MemberDto;
import com.example.highgrade.entity.Members;
import com.example.highgrade.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/member")
    public ResponseEntity<Members> saveMember(@RequestBody MemberDto dto){

        Members savedMember = memberService.saveMember(dto);
        return ResponseEntity.ok(savedMember);
    }
}
