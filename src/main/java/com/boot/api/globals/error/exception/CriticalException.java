package com.boot.api.globals.error.exception;


import com.boot.api.globals.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class CriticalException extends RuntimeException {
    private final String message;
    private final ErrorCode errorCode;

    public CriticalException(String message) {
        super(message);

        this.message = message;
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
