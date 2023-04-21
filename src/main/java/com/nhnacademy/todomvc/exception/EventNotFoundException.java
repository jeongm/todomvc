package com.nhnacademy.todomvc.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(long eventId) {
        super("이벤트가 존재하지 않습니다. eventId : "+ eventId );
    }
}
