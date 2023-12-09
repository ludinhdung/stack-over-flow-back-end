package com.stackoverflowbackend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record QuestionDto(Long id, @NotBlank String title, @NotBlank @Size(max = 513) String body,
                          @NotEmpty List<String> tags,
                          @JsonProperty("user_id") Long userId,
                          @JsonProperty("user_name") String username) {
}