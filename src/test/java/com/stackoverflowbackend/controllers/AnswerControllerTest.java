package com.stackoverflowbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackoverflowbackend.dtos.AnswerDto;
import com.stackoverflowbackend.services.answer.AnswerService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerController.class)
class AnswerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AnswerService answerService;

    @Test
    void testAddAnswerSuccess() throws Exception {
        AnswerDto answer = AnswerDto.builder().id(1L).body("Good job men").approve(false).userId(1L).questionId(1L).build();

        String json = objectMapper.writeValueAsString(answer);

        AnswerDto responseAnswer = AnswerDto.builder().id(1L).approve(false).build();

        given(answerService.postAnswer(answer)).willReturn(responseAnswer);

        mockMvc.perform(post("/api/v1/answers").accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id", Is.is(1)),
                        jsonPath("$.approve", Is.is(false))
                );
    }

    @Test
    void testApproveAnswer() throws Exception {
        AnswerDto responseAnswer = AnswerDto.builder().id(1L).approve(true).build();

        given(answerService.approveAnswer(anyLong())).willReturn(responseAnswer);

        mockMvc.perform(get("/api/v1/answers/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", Is.is(1)),
                        jsonPath("$.approve", Is.is(true))
                );
    }
}