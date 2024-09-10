package com.boot.api.globals.error.exception;

import com.boot.api.globals.common.enums.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String value) {
        super(value, ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
