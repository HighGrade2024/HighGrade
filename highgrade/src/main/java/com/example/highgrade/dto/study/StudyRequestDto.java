package com.example.highgrade.dto.study;

import com.example.highgrade.entity.Study;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class StudyRequestDto {
    private final String studyName;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private final LocalDate studyDate;
    private final String location;

    @Builder
    public StudyRequestDto(String studyName,
                           LocalDate studyDate,
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
