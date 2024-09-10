package com.boot.api.globals.error.exception;

import com.boot.api.globals.common.enums.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class BusinessException extends RuntimeException {

    private final String message;
    private final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);

        this.message = message;
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        this(null, errorCode);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
