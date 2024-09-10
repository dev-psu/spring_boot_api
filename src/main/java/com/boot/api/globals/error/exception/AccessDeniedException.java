package com.boot.api.globals.error.exception;

import com.boot.api.globals.common.enums.ErrorCode;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException() {
        super(ErrorCode.HANDLE_ACCESS_DENIED);
    }

}
