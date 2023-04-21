package com.nhnacademy.todomvc.controller;

import com.nhnacademy.todomvc.domain.Count;
import com.nhnacademy.todomvc.domain.Event;
import com.nhnacademy.todomvc.domain.TodoDateRequest;
import com.nhnacademy.todomvc.domain.TodoRegisterRequest;
import com.nhnacademy.todomvc.exception.BedRequestException;
import com.nhnacademy.todomvc.repository.TodoRepository;
import com.nhnacademy.todomvc.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/calendar")
public class TodoRestController {

    private TodoService todoService;

    public TodoRestController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 조회
    @GetMapping("/events")
    public ResponseEntity<List> eventViewByDay(@Valid @ModelAttribute TodoDateRequest todoDateRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new BedRequestException();
        }
        return ResponseEntity
                .ok()
                .body(todoService.getEvent(todoDateRequest));
    }

    //한개 조회-id
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> eventViewById(@PathVariable("id") String id){
        Event eventById =todoService.getEventById(id);
        return new ResponseEntity<>(eventById,HttpStatus.OK);
    }

    // 등록
    @PostMapping("/events")
    public ResponseEntity<Long> eventRegister(@Valid @RequestBody TodoRegisterRequest registerRequest){
         Long eventId = todoService.saveEventRegister(registerRequest);
        return new ResponseEntity<>(eventId, HttpStatus.CREATED);
    }

    //일일 투두 카운트
    @GetMapping("/daily-register-count")
    public Count eventCountByDaily(@RequestParam("date") String eventAt ){
        Count count = todoService.getTodoCount(eventAt);
        return count;
    }

//
    //삭제- 하나
    @DeleteMapping("/events/{id}")
    public void eventDeleteById(@PathVariable("id") String id){
        todoService.deleteEventById(id);
    }
    //삭제- 일단위
    @DeleteMapping("/events/daily/{eventAt}")
    public void eventDeleteByDaily(@PathVariable("eventAt") String eventAt){
        todoService.deleteEventByDaily(eventAt);
    }







}
