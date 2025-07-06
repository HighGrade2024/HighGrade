package com.example.highgrade.controller;

import com.example.highgrade.dto.study.StudyRequestDto;
import com.example.highgrade.dto.study.StudyResponseDto;
import com.example.highgrade.entity.MemberDetail;
import com.example.highgrade.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StudyController {

    private final StudyService studiesService;

    @PostMapping("/studies")
    public ResponseEntity<StudyResponseDto> createStudy(@RequestBody final StudyRequestDto dto,
                                                        @AuthenticationPrincipal MemberDetail memberDetail){
        return ResponseEntity.ok().body(studiesService.createStudy(dto, memberDetail.getEmail()));
    }

    @GetMapping("/studies/{id}")
    public ResponseEntity<StudyResponseDto> getStudy(@PathVariable final Long id){
        return ResponseEntity.ok().body(studiesService.getStudy(id));
    }

    @PutMapping("/studies/{id}")
    public ResponseEntity<StudyResponseDto> updateStudy(@PathVariable final Long id,
                                             @RequestBody final StudyRequestDto dto,
                                             @AuthenticationPrincipal MemberDetail memberDetail) throws AccessDeniedException {
        return ResponseEntity.ok().body(studiesService.updateStudy(id, dto, memberDetail.getEmail()));
    }

    @DeleteMapping("/studies/{id}")
    public ResponseEntity<Void> deleteStudy(@PathVariable final Long id){
        return studiesService.deleteStudy(id);
    }
}
