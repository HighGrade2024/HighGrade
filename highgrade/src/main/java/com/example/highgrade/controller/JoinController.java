package com.example.highgrade.controller;

import com.example.highgrade.dto.JoinResponseDto;
import com.example.highgrade.entity.MemberDetail;
import com.example.highgrade.service.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join/studies/{id}")
    public ResponseEntity<JoinResponseDto> join(@AuthenticationPrincipal MemberDetail memberDetail,
                                                @PathVariable final Long id){
        String email = memberDetail.getEmail();
        return ResponseEntity.ok().body(joinService.join(email, id));
    }
}
