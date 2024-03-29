package com.nhnacademy.todomvc.domain;

import lombok.Getter;

import java.time.LocalDateTime;


public class Event {
    @Getter
    private Long id;
    @Getter
    private String eventAt;
    @Getter
    private String subject;
    @Getter
    private LocalDateTime createAt;

    public Event() { }

    public Event(Long id, String eventAt, String subject) {
        this.id = id;
        this.eventAt = eventAt;
        this.subject = subject;
        this.createAt = LocalDateTime.now();
    }

}
