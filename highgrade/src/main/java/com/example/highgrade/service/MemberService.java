package com.example.highgrade.service;

import com.example.highgrade.dto.MemberDto;
import com.example.highgrade.entity.Members;
import com.example.highgrade.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Members saveMember(MemberDto dto){
        Members member = dto.toEntity(dto.getName());
        return memberRepository.save(member);
    }

    @Transactional
    public Members findMember(Long id){
        return memberRepository.findById(id).orElseThrow(
            NoSuchElementException::new
        );
    }
}
