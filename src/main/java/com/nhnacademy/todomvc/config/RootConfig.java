package com.nhnacademy.todomvc.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.todomvc.repository.MapTodoRepository;
import com.nhnacademy.todomvc.repository.TodoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;


@Configuration
@ComponentScan(basePackageClasses = {com.nhnacademy.todomvc.Base.class},
        excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public TodoRepository todoRepository(){
        TodoRepository todoRepository = new MapTodoRepository();
        todoRepository.save("2023-04-19","a");
        todoRepository.save("2023-04-19","b");
        todoRepository.save("2023-04-19","c");
        todoRepository.save("2023-04-19","d");
        todoRepository.save("2023-04-19","e");
        todoRepository.save("2023-04-20","1");
        todoRepository.save("2023-04-20","2");
        todoRepository.save("2023-04-20","3");
        todoRepository.save("2023-04-20","4");
        todoRepository.save("2023-04-20","5");

        return todoRepository;
    }

}
