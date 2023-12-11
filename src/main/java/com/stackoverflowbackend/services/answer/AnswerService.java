package com.stackoverflowbackend.services.answer;

import com.stackoverflowbackend.dtos.AnswerDto;
import jakarta.validation.Valid;

public interface AnswerService {
    AnswerDto postAnswer(AnswerDto answerDto);

    AnswerDto approveAnswer(Long answerId);
}
