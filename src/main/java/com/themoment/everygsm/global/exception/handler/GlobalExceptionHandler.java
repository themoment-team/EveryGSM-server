package com.themoment.everygsm.global.exception.handler;

import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.exception.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ErrorResponse.toResponseEntity("enum에 존재하지 않는 카테고리 입니다", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getDetailMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(Exception e) {
        log.error("UnknownException", e);
        return ErrorResponse.toResponseEntity("예외 처리되지 않은 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
