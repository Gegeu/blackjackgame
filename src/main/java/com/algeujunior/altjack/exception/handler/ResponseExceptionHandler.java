package com.algeujunior.altjack.exception.handler;

import com.algeujunior.altjack.exception.exceptions.CustomEntityNotFoundException;
import com.algeujunior.altjack.exception.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleLeadEntityNotFoundExceptions(CustomEntityNotFoundException ex) {

        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),
                ex.getMessage(), null);
        log.error("Not Found Exception: {}", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleLeadDefaultExceptions(Exception ex) {

        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),
                ex.getMessage(), null);
        log.error("Default Runtime Exception: {}", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleLeadMethodNotSupportedExceptions(Exception ex) {

        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),
                ex.getMessage(), null);
        log.error("Method Not Supported Exception: {}", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
