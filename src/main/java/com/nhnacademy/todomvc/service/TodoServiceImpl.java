package com.nhnacademy.todomvc.service;

import com.nhnacademy.todomvc.domain.Count;
import com.nhnacademy.todomvc.domain.Event;
import com.nhnacademy.todomvc.domain.TodoDateRequest;
import com.nhnacademy.todomvc.domain.TodoRegisterRequest;
import com.nhnacademy.todomvc.exception.EventNotFoundException;
import com.nhnacademy.todomvc.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Service
public class TodoServiceImpl implements TodoService{
    private TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    @Override
    public List getEvent(@Valid TodoDateRequest todoDateRequest) {
        String month = String.valueOf(todoDateRequest.getMonth());
        String day = String.valueOf(todoDateRequest.getDay());


        if(todoDateRequest.getMonth() < 10){
            month = "0"+month;
        }

        if(todoDateRequest.getDay() < 10){
            day = "0"+day;
        }

        if(todoDateRequest.getDay()== 0){// 월단위
            List<Event> eventByMonth = todoRepository.getEventByMonth(todoDateRequest.getYear()+"-"+month);
            return eventByMonth;
        }
        // 일단위
        List<Event> eventByDay = todoRepository.getTodoItemList(todoDateRequest.getYear()+"-"+month+"-"+day);
        return eventByDay;

    }

    @Override
    public Event getEventById(String id) {

        long targetId = Long.parseLong(id);
        if(Objects.isNull(todoRepository.getEventById(targetId))){
            throw new EventNotFoundException(targetId);
        }
        return todoRepository.getEventById(targetId);
    }

    @Override
    public Long saveEventRegister(TodoRegisterRequest registerRequest) {
        Long eventId =todoRepository.save(registerRequest.getEventAt(), registerRequest.getSubject());
        return eventId;
    }

    @Override
    public Count getTodoCount(String todoDate) {
        Count count = new Count(todoRepository.countByTodoDate(todoDate));
        return count;
    }

    @Override
    public void deleteEventById(String id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void deleteEventByDaily(String eventAt) {
        todoRepository.deleteByTodoDate(eventAt);
    }
}
