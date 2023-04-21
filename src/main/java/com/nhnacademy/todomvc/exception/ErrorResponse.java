package com.nhnacademy.todomvc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private HttpStatus statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String error;

    public ErrorResponse(HttpStatus statusCode, String message) {
        this.statusCode =statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus statusCode, String error, String path) {
        this.statusCode =statusCode;
        this.error = error;
        this.path=path;
        this.timestamp = LocalDateTime.now();
    }
}
