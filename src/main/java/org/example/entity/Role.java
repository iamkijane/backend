package org.example.entity;

import lombok.Getter;

public enum Role {
    USER,
    ADMIN;

    public String getRole() {
        return this.name();
    }
}