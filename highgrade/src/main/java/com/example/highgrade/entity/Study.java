package com.example.highgrade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String studyName;
    @NotNull
    private LocalDateTime studyDate;
    @NotNull
    private String location;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member master;

    @Builder
    public Study(Long id, String studyName,
                 String studyDate,
                 String location){
        this.id = id;
        this.studyDate = LocalDateTime.parse(studyDate);
        this.studyName = studyName;
        this.location = location;
    }

}
