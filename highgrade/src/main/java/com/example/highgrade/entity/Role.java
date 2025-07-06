package com.example.highgrade.entity;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    MEMBER("MEMBER"),
    OWNER("OWNER"),
    PARTICIPANT("PARTICIPANT");

    public final String name;

    Role(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return this.name.equals("ADMIN");
    }
}
