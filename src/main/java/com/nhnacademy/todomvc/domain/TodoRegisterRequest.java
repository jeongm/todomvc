package com.nhnacademy.todomvc.domain;

import lombok.Getter;

public class TodoRegisterRequest {
    @Getter
    private String subject;
    @Getter
    private String eventAt;
}
