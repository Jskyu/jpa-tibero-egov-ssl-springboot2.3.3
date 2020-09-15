package com.example.demo.vo.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN("Role_Admin"),
    MEMBER("Role_Member"),
    ANONYMOUS("Role_Anonymous");
    private String value;
    }
