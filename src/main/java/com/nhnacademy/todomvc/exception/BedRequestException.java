package com.nhnacademy.todomvc.exception;

public class BedRequestException extends RuntimeException{
    public BedRequestException() {
        super("Unauthorized");
    }
}
