package com.nhnacademy.todomvc.repository;
import com.nhnacademy.todomvc.domain.Event;
import com.nhnacademy.todomvc.exception.EventNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class MapTodoRepository implements TodoRepository{
    //ConcurrentMap<String, List<Event>>-> key: 날짜, id autoIdx
    private final ConcurrentMap<String, List<Event>> eventMap = new ConcurrentHashMap<>();
    private final AtomicLong autoIdx = new AtomicLong();


    @Override
    public List<Event> getTodoItemList(String todoDate) {
        // 일별 todolist
        List<Event> todoItemList = new ArrayList<>();
        for (Map.Entry<String,List<Event>> events: eventMap.entrySet()) {
            if(events.getKey().equals(todoDate)){
                todoItemList = events.getValue();
            }
        }
        return todoItemList;
    }

    @Override
    public List<Event> getEventByMonth(String yearMonth) {
        List<Event> targetEvents = new ArrayList<>();
        for (Map.Entry<String,List<Event>> events: eventMap.entrySet()) {
            if(events.getKey().startsWith(yearMonth)){
                for (Event event: events.getValue()) {
                    targetEvents.add(event);
                }
            }
        }
        return targetEvents;
    }

    @Override
    public Event getEventById(long id) {
        Event target = new Event();

        for(List<Event> events : eventMap.values()){
            for (Event event: events) {
                if(event.getId()==id){
                    target = event;
                    return target;
                }
            }
        }
        throw new EventNotFoundException(id);
    }

    @Override
    public Long save(String eventAt, String subject) {
        List<Event> eventList;
        if(Objects.isNull(eventMap.get(eventAt))){
            eventList = new ArrayList<>();
        }else {
            eventList= eventMap.get(eventAt);
        }
        Event event= new Event(autoIdx.getAndIncrement(),eventAt,subject);
        eventList.add(event);
        eventMap.put(eventAt,eventList);
        return event.getId();
    }

    @Override
    public void deleteByTodoDate(String todoDate) {
        if(Objects.nonNull(eventMap.get(todoDate))){
            eventMap.remove(todoDate);
        }
    }

    @Override
    public void deleteById(String id) {

        for(Map.Entry<String,List<Event>> events : eventMap.entrySet()){
            String date = events.getKey();
            for (Event event: events.getValue()) {
                if(event.getId() == Long.parseLong(id)){
                    eventMap.get(date).remove(event);
                    return;
                }
            }
        }
    }


    @Override
    public Integer countByTodoDate(String todoDate) {
        if(Objects.isNull(eventMap.get(todoDate))){
            return 0;
        }

        return eventMap.get(todoDate).size();

    }


}
