package com.example.highgrade.service;

import com.example.highgrade.dto.JoinResponseDto;
import com.example.highgrade.entity.Member;
import com.example.highgrade.entity.Study;
import com.example.highgrade.entity.StudyMember;
import com.example.highgrade.repository.MemberRepository;
import com.example.highgrade.repository.StudyMemberRepository;
import com.example.highgrade.repository.StudyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final StudyMemberRepository studyMemberRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final static String STUDY = "study";


    @Transactional
    public JoinResponseDto join(final String email, final Long groupId) {
        Member foundMember = memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        Study foundStudy = studyRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        StudyMember newStudyMember = StudyMember.builder()
            .study(foundStudy)
            .member(foundMember)
            .joinedAt(LocalDateTime.now())
            .build();
        studyMemberRepository.save(newStudyMember);
        return JoinResponseDto.builder()
            .groupType(STUDY)
            .groupId(foundStudy.getId())
            .memberId(foundMember.getId())
            .build();
    }
}
