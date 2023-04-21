package com.nhnacademy.todomvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.todomvc.config.RootConfig;
import com.nhnacademy.todomvc.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy(value = {
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {WebConfig.class})
})
class TodoControllerTest {

    @Autowired
    private WebApplicationContext context;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8"))
                .build();
    }

    @Test
    void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/index"))
                .andReturn();
    }
}