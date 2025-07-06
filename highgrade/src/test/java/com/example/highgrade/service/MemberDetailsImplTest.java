package com.example.highgrade.service;

import com.example.highgrade.entity.Member;
import com.example.highgrade.entity.MemberDetail;
import com.example.highgrade.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class MemberDetailsImplTest {

    @Test
    @DisplayName("MemberDetailsImpl 생성 및 권한 테스트")
    void memberDetailsImpl() {
        // given
        Member member = Member.builder().email("test@test.com").password("password").role(Role.MEMBER).build();

        // when
        MemberDetail memberDetails = new MemberDetail(member);

        // then
        assertThat(memberDetails.getUsername()).isEqualTo("test@test.com");
        assertThat(memberDetails.getPassword()).isEqualTo("password");
        Collection<? extends GrantedAuthority> authorities = memberDetails.getAuthorities();
        assertThat(authorities).hasSize(1);
        assertThat(authorities.iterator().next().getAuthority()).isEqualTo("ROLE_MEMBER");
    }
}
