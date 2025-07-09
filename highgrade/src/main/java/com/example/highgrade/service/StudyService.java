package com.example.highgrade.service;

import com.example.highgrade.config.security.TokenProvider;
import com.example.highgrade.dto.study.StudyRequestDto;
import com.example.highgrade.dto.study.StudyResponseDto;
import com.example.highgrade.entity.*;
import com.example.highgrade.repository.MemberRepository;
import com.example.highgrade.repository.StudyMemberRepository;
import com.example.highgrade.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public StudyResponseDto createStudy(final StudyRequestDto dto, final String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Study newStudy = Study.builder()
            .studyName(dto.getStudyName())
            .studyDate(dto.getStudyDate())
            .createdBy(member)
            .location(dto.getLocation())
            .build();
        Study savedStudy = studyRepository.save(newStudy);
        StudyMember newStudyMember = StudyMember.builder()
            .member(member)
            .study(newStudy)
            .build();
        return savedStudy.toResponseDto(newStudyMember);
    }

    @Transactional
    public StudyResponseDto getStudy(final Long id) {
        Study foundStudy = studyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return StudyResponseDto.builder()
            .studyId(foundStudy.getId())
            .studyName(foundStudy.getStudyName())
            .createdById(foundStudy.getCreatedBy().getId())
            .studyDate(foundStudy.getStudyDate())
            .updatedAt(foundStudy.getUpdatedAt())
            .location(foundStudy.getLocation())
            .createdAt(foundStudy.getCreatedAt())
            .build();
    }

    @Transactional
    public List<StudyResponseDto> getAllStudy() {
        List<Study> studyList =  studyRepository.findAll();
        List<StudyResponseDto> result = new ArrayList<>();
        for(Study study : studyList){
            result.add(StudyResponseDto.builder()
                .createdById(study.getCreatedBy().getId())
                .build());
        }
        return result;
    }

    @Transactional
    public StudyResponseDto updateStudy(final Long id, final StudyRequestDto dto, final String email) throws AccessDeniedException {
        Study foundStudy = studyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if(!foundStudy.getCreatedBy().getEmail().equals(email)){
            throw new AccessDeniedException("스터디 수정 권한이 없습니다.");
        }
        Study modifiedStudy = dto.toEntity(id);
        StudyResponseDto studyResponseDto = modifiedStudy.toDto();
        studyRepository.save(modifiedStudy);
        return studyResponseDto;
    }

    @Transactional
    public ResponseEntity<Void> deleteStudy(final Long id) {
        studyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
