package com.example.highgrade.service;

import com.example.highgrade.config.security.TokenProvider;
import com.example.highgrade.dto.StudyRequestDto;
import com.example.highgrade.entity.Member;
import com.example.highgrade.entity.Role;
import com.example.highgrade.entity.Study;
import com.example.highgrade.entity.StudyMember;
import com.example.highgrade.repository.MemberRepository;
import com.example.highgrade.repository.StudyMemberRepository;
import com.example.highgrade.repository.StudyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(StudyService.class));

    private static final String AUTHORIZATION = "Authorization";
    private final TokenProvider tokenProvider;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public ResponseEntity<Study> createStudy(StudyRequestDto dto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Study newStudy = Study.builder()
            .studyName(dto.getStudyName())
            .studyDate(dto.getStudyDate())
            .location(dto.getLocation())
            .build();
        StudyMember newStudyMember = StudyMember.builder()
            .study(newStudy)
            .member(member)
            .role(Role.MASTER)
            .build();
        Study savedStudy= studyRepository.save(newStudy);
        StudyMember savedStudyMember = studyMemberRepository.save(newStudyMember);
        return ResponseEntity.ok().body(savedStudy);
    }

    @Transactional
    public ResponseEntity<Study> getStudy(final Long id) {
        Study foundStudy = studyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.ok().body(foundStudy);
    }

    @Transactional
    public ResponseEntity<Study> updateStudy(final Long id, final StudyRequestDto dto) {
        Study foundStudy = studyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Study modifiedStudy = dto.toEntity(id);
        Study savedStudy = studyRepository.save(modifiedStudy);
        return ResponseEntity.ok().body(savedStudy);
    }

    @Transactional
    public ResponseEntity<Void> deleteStudy(final Long id) {
        studyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
