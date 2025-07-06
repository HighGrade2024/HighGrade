package com.example.highgrade.dto.study;

import com.example.highgrade.entity.Study;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyRequestDto {
    private final String studyName;
    private final LocalDateTime studyDate;
    private final String location;

    @Builder
    public StudyRequestDto(String studyName,
                           LocalDateTime studyDate,
                           String location){
        this.studyDate = studyDate;
        this.studyName = studyName;
        this.location = location;
    }

    public Study toEntity(Long id){
        return Study.builder()
            .id(id)
            .studyDate(studyDate)
            .studyName(studyName)
            .location(location)
            .build();
    }
}
