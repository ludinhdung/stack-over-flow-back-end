package com.stackoverflowbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackoverflowbackend.dtos.AllQuestionResponseDto;
import com.stackoverflowbackend.dtos.QuestionDto;
import com.stackoverflowbackend.exceptions.ObjectNotFoundException;
import com.stackoverflowbackend.services.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class QuestionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    QuestionService questionService;

    @Test
    void testPostQuestionSuccess() throws Exception {
        QuestionDto questionDto = QuestionDto.builder().title("title1").body("body").tags(List.of("tag1", "tag2")).userId(1L).build();
        QuestionDto responseQuestion = QuestionDto.builder().id(1L).title("title1").build();

        String json = objectMapper.writeValueAsString(questionDto);

        given(questionService.addQuestion(any(QuestionDto.class))).willReturn(responseQuestion);

        mockMvc.perform(post("/api/v1/questions").accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title1")));
    }

    @Test
    void testPostQuestionWithInvalidInput() throws Exception {
        QuestionDto questionDto = QuestionDto.builder().title(null).body(null).tags(List.of("tag1", "tag2")).userId(1L).build();
        QuestionDto responseQuestion = QuestionDto.builder().id(1L).title("title1").build();

        String json = objectMapper.writeValueAsString(questionDto);

        given(questionService.addQuestion(any(QuestionDto.class))).willReturn(responseQuestion);

        mockMvc.perform(post("/api/v1/questions").accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("must not be blank")))
                .andExpect(jsonPath("$.body", is("must not be blank")));
    }

    @Test
    void testPostQuestionWithNonExistUserId() throws Exception {
        QuestionDto questionDto = QuestionDto.builder().title("title1").body("body").tags(List.of("tag1", "tag2")).userId(1L).build();
        String json = objectMapper.writeValueAsString(questionDto);

        given(questionService.addQuestion(any(QuestionDto.class))).willThrow(new ObjectNotFoundException("answer",1L));

        mockMvc.perform(post("/api/v1/questions").accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0]", is("Could not find answer with Id 1")));
    }

    @Test
    void testGetAllQuestionsSuccess() throws Exception {
        QuestionDto questionDtoResponseAll1 = QuestionDto.builder().title("title1").body("body").tags(List.of("tag1", "tag2")).userId(1L).username("user 1").build();
        QuestionDto questionDtoResponseAll2 = QuestionDto.builder().title("title1").body("body").tags(List.of("tag1", "tag2")).userId(1L).username("user 1").build();

        AllQuestionResponseDto allQuestionResponseDto = AllQuestionResponseDto.builder()
                .questionList(List.of(questionDtoResponseAll1, questionDtoResponseAll2))
                .pageNumber(1)
                .totalPage(1)
                .build();

        given(questionService.getAllQuestions(1)).willReturn(allQuestionResponseDto);

        mockMvc.perform(get("/api/v1/questions").accept(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "1"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.total_page", is(1)),
                        jsonPath("$.page_number", is(1)),
                        jsonPath("$.question_list.size()", is(2))
                );
    }
}