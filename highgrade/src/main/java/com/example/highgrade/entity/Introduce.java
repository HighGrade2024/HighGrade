package com.example.highgrade.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Introduce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImageUrl;

    private String job;

    private String content;

    @OneToOne
    @JoinColumn(name = "members_id")
    private Members members;
}
