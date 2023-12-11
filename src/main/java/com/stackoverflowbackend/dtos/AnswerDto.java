package com.stackoverflowbackend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record AnswerDto(Long id, @NotBlank @Size(max = 512) String body, @JsonProperty("user_id") Long userId,
                        @JsonProperty("question_id") Long questionId, String username,
                        @JsonProperty("file") ImageDto file, boolean approve) implements Serializable {
}