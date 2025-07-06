package com.example.highgrade.service;

import com.example.highgrade.dto.JoinResponseDto;
import com.example.highgrade.entity.Member;
import com.example.highgrade.entity.Study;
import com.example.highgrade.entity.StudyMember;
import com.example.highgrade.repository.MemberRepository;
import com.example.highgrade.repository.StudyMemberRepository;
import com.example.highgrade.repository.StudyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class JoinServiceTest {

    @InjectMocks
    private JoinService joinService;

    @Mock
    private StudyRepository studyRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StudyMemberRepository studyMemberRepository;

    @Test
    @DisplayName("스터디 가입 테스트")
    void joinStudy() {
        // given
        Member member = Member.builder().email("test@test.com").build();
        Study study = Study.builder().id(1L).build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.of(member));
        when(studyRepository.findById(1L)).thenReturn(Optional.of(study));
        when(studyMemberRepository.save(any(StudyMember.class))).thenReturn(null);

        // when
        ResponseEntity<JoinResponseDto> response = ResponseEntity.ok().body(joinService.join(member.getEmail(), 1L));

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    @DisplayName("스터디 가입 테스트2")
    void joinStudy2() {
        // given
        String email = "test@test.com";
        Long studyId = 1L;
        Member member = Member.builder().email(email).build();
        Study study = Study.builder().id(studyId).build();

        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        given(studyRepository.findById(studyId)).willReturn(Optional.of(study));

        // when
        JoinResponseDto response = joinService.join(email, studyId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getGroupId()).isEqualTo(studyId);
        assertThat(response.getMemberId()).isEqualTo(member.getId());
        then(memberRepository).should().findByEmail(email);
        then(studyRepository).should().findById(studyId);
        assertThat(response.getMemberId()).isEqualTo(member.getId());
    }

    @Test
    void join_success() {
        // given
        String email = "test@test.com";
        Long studyId = 1L;

        Member member = Member.builder().id(10L).email(email).build();
        Study study = Study.builder().id(studyId).build();
        StudyMember join = StudyMember.builder().member(member).study(study).build();

        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        given(studyRepository.findById(studyId)).willReturn(Optional.of(study));
        given(studyMemberRepository.save(any(StudyMember.class))).willReturn(join);

        // when
        JoinResponseDto result = joinService.join(email, studyId);

        // then
        assertThat(result.getMemberId()).isEqualTo(10L);
        assertThat(result.getGroupId()).isEqualTo(1L);

        then(studyMemberRepository).should().save(any(StudyMember.class));
    }
}
