package com.stackoverflowbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackoverflowbackend.dtos.VoteDto;
import com.stackoverflowbackend.models.VoteType;
import com.stackoverflowbackend.services.vote.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VoteController.class)
@AutoConfigureMockMvc(addFilters = false)
class VoteControllerTest {

    @MockBean
    VoteService voteService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testAddVoteSuccess() throws Exception {
        VoteDto voteDto = VoteDto.builder().id(1L).type(VoteType.UPVOTE).build();

        VoteDto responseVote = VoteDto.builder().id(1L).build();

        String json = objectMapper.writeValueAsString(voteDto);

        given(voteService.addVoteToQuestion(any(VoteDto.class))).willReturn(responseVote);

        mockMvc.perform(post("/api/v1/vote").accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id", is(1))
                );
    }

}