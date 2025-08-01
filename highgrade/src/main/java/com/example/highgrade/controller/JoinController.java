package com.example.highgrade.controller;

import com.example.highgrade.dto.JoinResponseDto;
import com.example.highgrade.entity.MemberDetail;
import com.example.highgrade.service.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join/studies/{id}")
    public ResponseEntity<JoinResponseDto> join(@AuthenticationPrincipal MemberDetail memberDetail,
                                                @PathVariable final Long id) throws AccessDeniedException {
        String email = memberDetail.getEmail();
        return ResponseEntity.ok().body(joinService.join(email, id));
    }

    @PostMapping("/unjoin/studies/{id}")
    public void unjoin(@AuthenticationPrincipal MemberDetail memberDetail,
                                                @PathVariable final Long id) throws AccessDeniedException {
        String email = memberDetail.getEmail();
        joinService.unjoin(email, id);
    }

    @GetMapping("/join/studies")
    public ResponseEntity<List<JoinResponseDto>> getJoinList(@AuthenticationPrincipal MemberDetail memberDetail){
        String email = memberDetail.getEmail();
        return ResponseEntity.ok().body(joinService.getJoinList(email));
    }
}
