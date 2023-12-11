package com.stackoverflowbackend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ImageDto(Long id, @NotBlank String name, @NotBlank String type, byte[] data) {
}