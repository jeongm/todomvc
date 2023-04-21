package com.nhnacademy.todomvc.domain;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TodoDateRequest {
    @NotBlank
    @NotEmpty
    @Getter
    private String year;
    @NotNull
    @Getter
    private Integer month;
    @Getter
    private int day;
}
