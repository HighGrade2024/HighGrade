package com.example.highgrade.dto.study;

import com.example.highgrade.entity.Study;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyResponseDto {
    private final Long studyId;

    private final Long createdById;
    private final String studyName;
    private final LocalDateTime studyDate;
    private final String location;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public StudyResponseDto(final Long studyId, final Long createdById, final String studyName, final LocalDateTime studyDate, final String location, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.studyId = studyId;
        this.createdById = createdById;
        this.studyName = studyName;
        this.studyDate = studyDate;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public StudyResponseDto toResponseDto(Study study){
        return StudyResponseDto.builder()
            .updatedAt(study.getUpdatedAt())
            .studyDate(study.getStudyDate())
            .studyName(study.getStudyName())
            .studyId(study.getId())
            .createdAt(study.getCreatedAt())
            .location(study.getLocation())
            .createdById(study.getCreatedBy().getId())
            .build();
    }
}
