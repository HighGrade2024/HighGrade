package com.example.highgrade.entity;

import com.example.highgrade.dto.study.StudyResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String studyName;
    @Column(nullable = false)
    private LocalDateTime studyDate;
    @Column(nullable = false)
    private String location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Member createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Study(Long id, String studyName,
                 LocalDateTime studyDate,
                 String location, Member createdBy){
        this.id = id;
        this.studyDate =studyDate;
        this.createdBy = createdBy;
        this.studyName = studyName;
        this.location = location;
    }

    public StudyResponseDto toResponseDto(){
        return StudyResponseDto.builder()
            .updatedAt(this.updatedAt)
            .studyDate(this.studyDate)
            .studyName(this.studyName)
            .studyId(this.id)
            .createdAt(this.createdAt)
            .location(this.location)
            .createdById(this.createdBy.getId())
            .build();
    }
}
