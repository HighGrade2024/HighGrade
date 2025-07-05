package com.example.highgrade.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinResponseDto {
    private String groupType;
    private Long groupId;
    private Long memberId;

    @Builder
    public JoinResponseDto(String groupType, Long groupId, Long memberId){
        this.groupType = groupType;
        this.groupId = groupId;
        this.memberId = memberId;
    }
}
