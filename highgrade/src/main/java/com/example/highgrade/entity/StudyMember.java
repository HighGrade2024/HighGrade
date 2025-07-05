package com.example.highgrade.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class StudyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;
    @CreatedDate
    private LocalDateTime joinedAt;

    @Builder
    public StudyMember(Member member, Study study, LocalDateTime joinedAt){
        this.member = member;
        this.study = study;
        this.joinedAt = joinedAt;
    }
}
