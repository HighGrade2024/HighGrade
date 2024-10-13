package com.example.highgrade.service;

import com.example.highgrade.entity.Members;
import com.example.highgrade.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("멤버 저장 테스트")
    void saveMemberTest(){
        Members member = Members.builder().name("test").build();
        memberService.saveMember(member);

        when(memberService.findMember(1L))
            .thenReturn(member);

        Members foundMember = memberService.findMember(1L);
        assertEquals("test", foundMember.getName());
        System.out.println("foundMember id : " + foundMember.getName());
    }
}