package com.nhnacademy.todomvc.service;

import com.nhnacademy.todomvc.domain.Count;
import com.nhnacademy.todomvc.domain.Event;
import com.nhnacademy.todomvc.domain.TodoDateRequest;
import com.nhnacademy.todomvc.domain.TodoRegisterRequest;

import java.util.List;

public interface TodoService {
    //조회
    List getEvent(TodoDateRequest todoDateRequest);
    // 한개 조회 id
    Event getEventById(String id);
    //등록
    Long saveEventRegister(TodoRegisterRequest todoRegisterRequest);
    //일일 투두 카운트
    Count getTodoCount(String todoDate);
    //삭제- id
    void deleteEventById(String id);

    //삭제- 일단위
    void deleteEventByDaily(String eventAt);


}
