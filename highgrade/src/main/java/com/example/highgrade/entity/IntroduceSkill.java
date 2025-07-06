package com.example.highgrade.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class IntroduceSkill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "introduce_id")
    private Introduce introduce;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;
}
