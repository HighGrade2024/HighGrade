package com.example.highgrade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @NotNull
    private String studyName;
    @NotNull
    private LocalDateTime studyDate;
    @NotNull
    private String location;

    @Builder
    public Study(Long id, String studyName,
                 LocalDateTime studyDate,
                 String location){
        this.id = id;
        this.studyDate =studyDate;
        this.studyName = studyName;
        this.location = location;
    }

}
