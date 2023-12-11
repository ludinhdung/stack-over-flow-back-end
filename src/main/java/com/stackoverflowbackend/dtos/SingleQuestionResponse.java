package com.stackoverflowbackend.dtos;

import lombok.Builder;

import java.util.List;
@Builder
public record SingleQuestionResponse(QuestionDto question, List<AnswerDto> answers) {
}
