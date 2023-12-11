package com.stackoverflowbackend.services.question;

import com.stackoverflowbackend.dtos.AllQuestionResponseDto;
import com.stackoverflowbackend.dtos.QuestionDto;
import com.stackoverflowbackend.dtos.SingleQuestionResponse;

public interface QuestionService {
    QuestionDto addQuestion(QuestionDto questionDto);

    AllQuestionResponseDto getAllQuestions(Integer pageNumber);

    SingleQuestionResponse getQuestionById(Long questionId,Long userId);

    AllQuestionResponseDto getQuestionByUserId(Long userId, Integer pageNumber);
}
