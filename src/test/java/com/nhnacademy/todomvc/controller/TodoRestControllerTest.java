package com.nhnacademy.todomvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.todomvc.config.RootConfig;
import com.nhnacademy.todomvc.config.WebConfig;
import com.nhnacademy.todomvc.domain.TodoRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasXPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy(value = {
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {WebConfig.class})
})
class TodoRestControllerTest {

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
    @DisplayName("일 단위 조회")
    void testEventViewByDay() throws Exception {
        mockMvc.perform(get("/api/calendar/events")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("x-user-id","jeong")
                        .param("year","2023")
                        .param("month","04")
                        .param("day","19"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(5)))
                .andReturn();
    }

    @Test
    @DisplayName("월 단위 조회")
    void testEventViewByMonth() throws Exception {
        mockMvc.perform(get("/api/calendar/events")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("x-user-id","jeong")
                        .param("year","2023")
                        .param("month","4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(10)))
                .andReturn();
    }

    @Test
    @DisplayName("event 조회 - id")
    void testEventViewById() throws Exception {
        mockMvc.perform(get("/api/calendar/events/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("x-user-id","jeong")
                        .param("format","json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(4)))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.eventAt").value("2023-04-19"))
                .andExpect(jsonPath("$.subject").value("c"))
                .andReturn();
    }

    @Test
    @DisplayName("event 등록")
    void testEventRegister() throws Exception {
        TodoRegisterRequest todoRegisterRequest = new TodoRegisterRequest();
        ReflectionTestUtils.setField(todoRegisterRequest,"subject","hihih");
        ReflectionTestUtils.setField(todoRegisterRequest,"eventAt","2023-04-02");

        mockMvc.perform(post("/api/calendar/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRegisterRequest))
                        .header("x-user-id","jeong"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("일일등록 카운트")
    void testEventCountByDaily() throws Exception {
        mockMvc.perform(get("/api/calendar/daily-register-count?date=2023-04-20")
                .accept(MediaType.APPLICATION_JSON)
                .header("x-user-id","jeong"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(5))
                .andReturn();
    }

    @Test
    void testEventDeleteById() throws Exception {
        mockMvc.perform(delete("/api/calendar/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-user-id","jeong"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testEventDeleteByDaily() throws Exception {
        mockMvc.perform(delete("/api/calendar/events/daily/2022-04-19")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-user-id","jeong"))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}