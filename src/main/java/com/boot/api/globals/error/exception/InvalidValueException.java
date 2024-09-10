package com.boot.api.globals.error.exception;

import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.response.ResponseBody;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InvalidValueException extends BusinessException {

    List<ResponseBody.FieldError> errors = new ArrayList<>();

    public InvalidValueException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidValueException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }

    public InvalidValueException(String value, List<ResponseBody.FieldError> errors) {
        this(value);
        this.errors = errors;
    }
}
