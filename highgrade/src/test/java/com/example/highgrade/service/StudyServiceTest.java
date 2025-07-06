package com.example.highgrade.service;

import com.example.highgrade.dto.study.StudyRequestDto;
import com.example.highgrade.dto.study.StudyResponseDto;
import com.example.highgrade.entity.Member;
import com.example.highgrade.entity.Study;
import com.example.highgrade.repository.MemberRepository;
import com.example.highgrade.repository.StudyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @InjectMocks
    private StudyService studyService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("스터디 생성 테스트")
    void createStudy() {
        // given
        StudyRequestDto requestDto = new StudyRequestDto("스터디", LocalDateTime.now(), "서울");
        Member member = Member.builder().email("test@test.com").build();
        Study study = Study.builder()
                .studyName(requestDto.getStudyName())
                .studyDate(requestDto.getStudyDate())
                .location(requestDto.getLocation())
                .createdBy(member)
                .build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.of(member));
        when(studyRepository.save(any(Study.class))).thenReturn(study);

        // when
        ResponseEntity<StudyResponseDto> response = ResponseEntity.ok().body(studyService.createStudy(requestDto, member.getEmail()));

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    @DisplayName("스터디 조회 테스트")
    void getStudy() {
        // given
        Study study = Study.builder().id(1L).build();
        when(studyRepository.findById(1L)).thenReturn(Optional.of(study));

        // when

    }

    @Test
    @DisplayName("스터디 수정 테스트")
    void updateStudy() {
        // given
        StudyRequestDto requestDto = new StudyRequestDto("스터디 수정", LocalDateTime.now(), "부산");
        Study study = Study.builder().id(1L).build();
        Study updatedStudy = Study.builder()
                .id(1L)
                .studyName(requestDto.getStudyName())
                .studyDate(requestDto.getStudyDate())
                .location(requestDto.getLocation())
                .build();

        when(studyRepository.findById(1L)).thenReturn(Optional.of(study));
        when(studyRepository.save(any(Study.class))).thenReturn(updatedStudy);

        // when

        // then
    }

    @Test
    @DisplayName("스터디 삭제 테스트")
    void deleteStudy() {
        // when
        ResponseEntity<Void> response = studyService.deleteStudy(1L);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
