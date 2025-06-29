package com.example.highgrade.entity;

public enum Role {
    ADMIN("admin"),
    MEMBER("member");

    public final String name;

    Role(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return this.name.equals("admin");
    }
}
