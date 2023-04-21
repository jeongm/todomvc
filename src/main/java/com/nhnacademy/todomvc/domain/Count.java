package com.nhnacademy.todomvc.domain;

import lombok.Getter;

public class Count {
    @Getter
    private int count;

    public Count(int count) {
        this.count = count;
    }
}
