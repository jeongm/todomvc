package com.nhnacademy.todomvc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Event {
    private int id;
    private String userId;
    private String subject;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime eventDt;
}
