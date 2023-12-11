package com.stackoverflowbackend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stackoverflowbackend.models.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record VoteDto(Long id, @NotNull VoteType type, @JsonProperty("user_id") Long userId,
                      @JsonProperty("question_id") Long questionId) implements Serializable {

}