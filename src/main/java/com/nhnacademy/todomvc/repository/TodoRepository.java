package com.nhnacademy.todomvc.repository;

import com.nhnacademy.todomvc.domain.Event;

import java.util.List;

public interface TodoRepository {
    List<Event> getTodoItemList(String todoDate);
    List<Event> getEventByMonth(String yearMonth);
    Event getEventById(long id);
    Long save(String eventAt,String subject);
    void deleteByTodoDate(String todoDate);
    void deleteById(String id);
    Integer countByTodoDate(String todoDate);

}
