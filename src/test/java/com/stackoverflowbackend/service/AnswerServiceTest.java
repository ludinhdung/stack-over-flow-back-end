package com.stackoverflowbackend.service;

import com.stackoverflowbackend.dtos.AnswerDto;
import com.stackoverflowbackend.mappers.AnswerMapper;
import com.stackoverflowbackend.models.Answer;
import com.stackoverflowbackend.models.Question;
import com.stackoverflowbackend.models.User;
import com.stackoverflowbackend.repositories.AnswerRepository;
import com.stackoverflowbackend.repositories.QuestionRepository;
import com.stackoverflowbackend.repositories.UserRepository;
import com.stackoverflowbackend.services.answer.AnswerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    AnswerRepository answerRepository;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    AnswerMapper answerMapper;

    @InjectMocks
    AnswerServiceImpl answerService;

    @Test
    void testPostAnswerSuccess() {
        User user = User.builder().build();
        Question question = Question.builder().id(1L).title("Title 1").build();
        Answer answer = Answer.builder().body("Body").build();
        AnswerDto answerDto = AnswerDto.builder().id(1L).approve(false).userId(1L).questionId(1L).build();

        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
        given(questionRepository.findById(anyLong())).willReturn(Optional.ofNullable(question));
        given(answerMapper.toEntity(any(AnswerDto.class))).willReturn(answer);
        given(answerRepository.save(any(Answer.class))).willReturn(answer);
        given(answerMapper.toDtoResponseCreate(any(Answer.class))).willReturn(answerDto);

        AnswerDto responseAnswer = answerService.postAnswer(answerDto);

        assertEquals(1L, responseAnswer.id());
        assertFalse(responseAnswer.approve());
    }
}