package com.boot.api.globals.error.exception;

import com.boot.api.globals.common.enums.ErrorCode;

public class NotSupportedAPIException extends BusinessException {

    public NotSupportedAPIException(String message) {
        super(message, ErrorCode.NOT_SUPPORTED_API);
    }
}
