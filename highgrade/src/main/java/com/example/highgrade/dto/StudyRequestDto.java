package com.example.highgrade.dto;

import com.example.highgrade.entity.Study;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class StudyRequestDto {
    private String studyName;
    private LocalDateTime studyDate;
    private String location;

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
