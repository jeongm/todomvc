package com.nhnacademy.todomvc.advice;

import com.nhnacademy.todomvc.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class WebControllerAdvice {
    @InitBinder
    void InitBinder(WebDataBinder binder){
        binder.initDirectFieldAccess();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionResponse> unauthorizedError(UnauthorizedException exception){

        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionResponse> accessDeniedError(AccessDeniedException exception){

        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.FORBIDDEN,exception.getMessage());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BedRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> badRequestError(BedRequestException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(exceptionResponse);
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> badRequestError(EventNotFoundException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND,exception.getMessage());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ExceptionResponse> badRequestError(HttpRequestMethodNotSupportedException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED,"Method Not Allowed",exception.getMessage());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> internalServerError(NumberFormatException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
