package com.example.highgrade.controller;

import com.example.highgrade.dto.StudyRequestDto;
import com.example.highgrade.entity.Study;
import com.example.highgrade.service.StudyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.StringTokenizer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StudyController {

    private final StudyService studiesService;

    @PostMapping("/studies")
    public ResponseEntity<Study> createStudy(@RequestBody StudyRequestDto dto){
        return studiesService.createStudy(dto);
    }

    @GetMapping("/studies/{id}")
    public ResponseEntity<Study> getStudy(@PathVariable final Long id){
        return studiesService.getStudy(id);
    }

    @PutMapping("/studies/{id}")
    public ResponseEntity<Study> updateStudy(@PathVariable final Long id,
                                             @RequestBody final StudyRequestDto dto){
        return studiesService.updateStudy(id, dto);
    }

    @DeleteMapping("/studies/{id}")
    public ResponseEntity<Void> deleteStudy(@PathVariable final Long id){
        return studiesService.deleteStudy(id);
    }
}
