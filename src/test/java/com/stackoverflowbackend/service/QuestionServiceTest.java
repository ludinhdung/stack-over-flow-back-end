package com.stackoverflowbackend.service;

import com.stackoverflowbackend.dtos.AllQuestionResponseDto;
import com.stackoverflowbackend.dtos.QuestionDto;
import com.stackoverflowbackend.exceptions.UserNotFoundException;
import com.stackoverflowbackend.mappers.QuestionMapper;
import com.stackoverflowbackend.models.Question;
import com.stackoverflowbackend.models.User;
import com.stackoverflowbackend.repositories.QuestionRepository;
import com.stackoverflowbackend.repositories.UserRepository;
import com.stackoverflowbackend.services.question.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    QuestionRepository questionRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    QuestionMapper questionMapper;

    @InjectMocks
    QuestionServiceImpl questionService;

    @Test
    void testAddQuestionSuccess() {
        //given
        User user = User.builder().build();
        Question question = Question.builder().build();

        QuestionDto questionDto = QuestionDto.builder().title("title1").body("body").tags(List.of("tag1", "tag2")).userId(1L).build();

        QuestionDto questionResponse = QuestionDto.builder().id(1L).title("title1").build();

        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
        given(questionMapper.toEntity(any(QuestionDto.class))).willReturn(question);
        given(questionRepository.save(any(Question.class))).willReturn(question);
        given(questionMapper.toDtoResponseCreate(any(Question.class))).willReturn(questionResponse);

        //when
        QuestionDto questionResultResponse = questionService.addQuestion(questionDto);

        //assert
        assertEquals(1L, questionResultResponse.id());
        assertEquals("title1", questionResultResponse.title());
        assertNull(questionResponse.body());
    }

    @Test
    void testAddQuestionNotFoundUserId() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        Throwable exception = assertThrows(UserNotFoundException.class, () -> questionService.addQuestion(QuestionDto.builder().userId(1L).build()));

        assertEquals("The user with id 1 was not not found", exception.getMessage());
    }

    @Test
    void testGetAllQuestionsSuccess() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(Question.builder().build());
        questionList.add(Question.builder().build());

        given(questionRepository.findAll(PageRequest.of(0, 5))).willReturn(new PageImpl<>(questionList));
        given(questionMapper.toDtoResponseAll(any())).willReturn(QuestionDto.builder().title("title").build());

        AllQuestionResponseDto resultReturn = questionService.getAllQuestions(1);

        assertAll(
                () -> assertEquals(2, resultReturn.questionList().size(), "Size should be 2"),
                () -> assertEquals(1, resultReturn.totalPage(), "Total page should be 1"),
                () -> assertEquals(1, resultReturn.pageNumber(), "Page number should be 1")
        );
    }
}
