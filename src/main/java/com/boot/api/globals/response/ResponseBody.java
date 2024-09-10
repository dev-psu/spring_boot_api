package com.boot.api.globals.response;

import com.boot.api.globals.common.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class ResponseBody<T> {
    private int status;
    private String code;
    private String message;
    private List<FieldError> errors;
    private T data;

    public ResponseBody() {
        this.status = 200;
        this.code = "";
        this.message = "";
        this.errors = new ArrayList<>();
        this.data = null;
    }

    public ResponseBody(T data) {
        this.status   = 200;
        this.code    = "";
        this.message = "";
        this.errors  = new ArrayList<>();
        this.data    = data;
    }

    private ResponseBody(ErrorCode code, List<FieldError> errors) {
        this.status  = code.getStatus();
        this.code    = code.getCode();
        this.message = code.getMessage();
        this.errors  = errors;
    }

    private ResponseBody(ErrorCode code) {
        this.status  = code.getStatus();
        this.code    = code.getCode();
        this.message = code.getMessage();
        this.errors  = new ArrayList<>();
    }

    public static ResponseBody success() {
        return new ResponseBody();
    }

    public static <T> ResponseBody<T> of(T data) {
        return new ResponseBody(data);
    }

    public static ResponseBody of(ErrorCode code, BindingResult bindingResult) {
        return new ResponseBody(code, FieldError.of(bindingResult));
    }

    public static ResponseBody of(ErrorCode code) {
        return new ResponseBody(code);
    }

    public static ResponseBody of(ErrorCode code, List<FieldError> errors) {
        return new ResponseBody(code, errors);
    }

    public static ResponseBody of(MethodArgumentTypeMismatchException e) {
        String value = Objects.isNull(e.getValue()) ? "" : e.getValue().toString();
        List<ResponseBody.FieldError> errors = ResponseBody.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ResponseBody(ErrorCode.INVALID_TYPE_VALUE, errors);
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(String field, String value, String reason) {
            List<FieldError> errors = new ArrayList<>();
            errors.add(new FieldError(field, value, reason));
            return errors;
        }

        private static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                .map(error -> new FieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()))
                .collect(Collectors.toList());
        }
    }

}
