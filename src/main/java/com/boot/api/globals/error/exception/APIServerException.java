package com.boot.api.globals.error.exception;

import com.boot.api.globals.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class APIServerException extends RuntimeException {

    private final String apiUrl;
    private final String apiParameters;
    private final String message;
    private final ErrorCode errorCode;

    public APIServerException(String url, String parameters, String message, ErrorCode errorCode) {
        super(message);

        this.apiUrl = url;
        this.apiParameters = parameters;
        this.message = message;
        this.errorCode = errorCode;
    }

    public APIServerException(String url, String parameters, String message) {
        this(url, parameters, message, ErrorCode.API_SERVER_ERROR);
    }

    public APIServerException(String message, ErrorCode errorCode) {
        this(null, null, message, errorCode);
    }
}
