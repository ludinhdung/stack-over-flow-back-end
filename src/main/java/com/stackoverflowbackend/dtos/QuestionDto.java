package com.stackoverflowbackend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class QuestionDto {
    Long id;

    @NotBlank
    String title;

    @NotBlank
    @Size(max = 512)
    String body;

    @NotEmpty
    List<String> tags;

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("user_name")
    String username;

    @JsonProperty("create_date")
    LocalDate createDate;

    @JsonProperty("vote_count")
    Integer voteCount;

    private int vote;

    boolean hasApproved;
}