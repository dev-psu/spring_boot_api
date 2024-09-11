package com.boot.api.globals.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private static final String OK_STATUS = HttpStatus.OK.toString();
    private String statusCode;
    private T data;

    public BaseResponse(String statusCode) {
        this.statusCode = statusCode;
    }

    public BaseResponse(String statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> BaseResponse<T> successWithNoData() { return new BaseResponse<>(OK_STATUS);}
    public static <T> BaseResponse<T> successWithData(T data) {return new BaseResponse<>(OK_STATUS, data);}
}
