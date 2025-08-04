package com.example.highgrade.dto.study;

import com.example.highgrade.entity.Study;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class StudyResponseDto {
    private final Long id;
    private final Long createdById;
    private final String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final String location;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int memberCount;
    private final List<String> participants;

    @Builder
    public StudyResponseDto(final List<String> participants, final int memberCount, final Long id, final Long createdById, final String name, LocalDate date, final String location, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.participants = participants;
        this.memberCount = memberCount;
        this.createdById = createdById;
        this.name = name;
        this.date = date;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
