package com.boot.api.globals.error;

import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.error.exception.AccessDeniedException;
import com.boot.api.globals.error.exception.BusinessException;
import com.boot.api.globals.error.exception.CriticalException;
import com.boot.api.globals.error.exception.InvalidValueException;
import com.boot.api.globals.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    /**
     *  jakarta.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("handleMethodArgumentNotValidException", e);
        ResponseBody response = ResponseBody.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ResponseBody> handleBindException(BindException e) {
        log.info("handleBindException", e);
        ResponseBody response = ResponseBody.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @RequestParams 값이 없을 때 MissingServletRequestParameterException 발생한다.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ResponseBody> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("handleMissingServletRequestParameterException", e);
        ResponseBody response = ResponseBody.of(ErrorCode.INVALID_INPUT_VALUE, ResponseBody.FieldError.of(e.getParameterName(), "", e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseBody> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("handleHttpMessageNotReadableException", e);
        ResponseBody response = ResponseBody.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 비지니스 로직에서 직접 throw 할 때
     */
    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<ResponseBody> handleInvalidValueException(InvalidValueException e) {
        log.info("handleInvalidValueException", e);
        ResponseBody response = ResponseBody.of(ErrorCode.INVALID_INPUT_VALUE, e.getErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ResponseBody> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("handleMethodArgumentTypeMismatchException", e);
        ResponseBody response = ResponseBody.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ResponseBody> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("handleHttpRequestMethodNotSupportedException", e);
        ResponseBody response = ResponseBody.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ResponseBody> handleAccessDeniedException(AccessDeniedException e) {
        log.info("handleAccessDeniedException", e);
        ResponseBody response = ResponseBody.of(ErrorCode.HANDLE_ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseBody> handleBusinessException(BusinessException e) {
        log.info("handleBusinessException", e);

        ErrorCode errorCode = e.getErrorCode();
        ResponseBody response = ResponseBody.of(errorCode);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(CriticalException.class)
    protected ResponseEntity<ResponseBody> handleCriticalException(CriticalException e) {
        log.error("handleCriticalException", e);

        ErrorCode errorCode = e.getErrorCode();
        ResponseBody response = ResponseBody.of(errorCode);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseBody> handleException(Exception e) {
        log.error("handleException", e);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ResponseBody response = ResponseBody.of(errorCode);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
