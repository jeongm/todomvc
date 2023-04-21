package com.nhnacademy.todomvc.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRegisterRequest {
    private String subject;
    private String eventAt;
}
