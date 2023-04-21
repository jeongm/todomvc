package com.nhnacademy.todomvc.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("Unauthorized");
    }
}
