package com.nhnacademy.todomvc.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private HttpStatus statusCode;
    private LocalDateTime timestamp;
    private String message;

    public ExceptionResponse(HttpStatus statusCode, LocalDateTime timestamp, String message) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
    }
}
