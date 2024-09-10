package com.boot.api.globals.error.exception;

import com.boot.api.globals.common.enums.ErrorCode;

public class LoggingException extends BusinessException {

    public LoggingException() {
        super(ErrorCode.LOGGING_FAIL);
    }
}
