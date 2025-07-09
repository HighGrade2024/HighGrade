package com.example.highgrade.repository;

import com.example.highgrade.entity.Member;
import com.example.highgrade.entity.Study;
import com.example.highgrade.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    Optional<StudyMember> findByMemberId(Long memberId);

     boolean existsByMemberAndStudy(Member member, Study study);
}
