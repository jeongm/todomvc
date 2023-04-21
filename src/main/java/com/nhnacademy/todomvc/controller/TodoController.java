package com.nhnacademy.todomvc.controller;
import com.nhnacademy.todomvc.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoController {

    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping({"/",""})
    public String index(){
        return "/index";
    }
}
