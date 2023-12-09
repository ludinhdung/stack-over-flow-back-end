package com.stackoverflowbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record AllQuestionResponseDto(@JsonProperty("question_list") List<QuestionDto> questionList,
                                     @JsonProperty("total_page") Integer totalPage,
                                     @JsonProperty("page_number") Integer pageNumber) {
}
