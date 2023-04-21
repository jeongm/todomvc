package com.nhnacademy.todomvc.domain;

import lombok.Getter;

import javax.validation.constraints.NotNull;

public class TodoDateRequest {
    @NotNull
    @Getter
    private String year;
    @NotNull
    @Getter
    private int month;
    @Getter
    private int day;
}
