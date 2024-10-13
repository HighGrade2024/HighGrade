package com.example.highgrade.dto;

import com.example.highgrade.entity.Members;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private String name;

    public MemberDto(String name){
        this.name = name;
    }
    public Members toEntity(String name){
        return Members.builder()
            .name(name)
            .build();
    }
}
