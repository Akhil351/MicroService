package org.Akhil.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CONFLICT("001409001"),
    NOT_FOUND("001404001"),
    UNAUTHORIZED("001401001"),
    INTERNAL_SERVER_ERROR("001500001"),
    BAD_REQUEST("001400001");
    private final   String errorCode;
    ErrorCode(String errorCode){
        this.errorCode=errorCode;
    }

}
