package org.Akhil.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    ADMIN(100,"Admin"),
    USER(101,"User");
    private final Integer code;
    private final String role;
    Role(Integer code, String role){
        this.role=role;
        this.code=code;
    }
    public static Integer code(String role){
        return Arrays.stream(Role.values())
                .filter(roles->roles.getRole().equalsIgnoreCase(role))
                .map(Role::getCode)
                .findFirst()
                .orElse(null);
    }
    public static String role(Integer code){
        return Arrays.stream(Role.values())
                .filter(roles->roles.getCode().equals(code))
                .map(Role::getRole)
                .findFirst()
                .orElse(null);
    }
}
