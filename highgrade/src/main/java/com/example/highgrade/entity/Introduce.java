package com.example.highgrade.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.List;

@Entity
@Getter
public class Introduce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImageUrl;

    private String job;
    @OneToMany
    private List<Skill> skills;

    private String content;

    @OneToOne
    @JoinColumn(name = "members_id")
    private Member members;
}
