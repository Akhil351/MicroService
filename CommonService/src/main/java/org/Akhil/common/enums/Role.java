package org.Akhil.common.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(100,"Admin"),
    USER(101,"Customer");
    private final Integer code;
    private final String role;
    Role(Integer code, String role){
        this.role=role;
        this.code=code;
    }

}
