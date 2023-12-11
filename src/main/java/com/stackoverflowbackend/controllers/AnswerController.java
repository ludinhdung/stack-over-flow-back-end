package com.stackoverflowbackend.controllers;

import com.stackoverflowbackend.dtos.AnswerDto;
import com.stackoverflowbackend.services.answer.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/answers")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerDto addAnswer(@RequestBody @Valid AnswerDto answerDto) {
        return answerService.postAnswer(answerDto);
    }

    @GetMapping("/{answerId}")
    public AnswerDto approveAnswer(@PathVariable Long answerId) {
        return answerService.approveAnswer(answerId);
    }
}
