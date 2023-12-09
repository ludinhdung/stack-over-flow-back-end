package com.stackoverflowbackend.controllers;

import com.stackoverflowbackend.dtos.AllQuestionResponseDto;
import com.stackoverflowbackend.dtos.QuestionDto;
import com.stackoverflowbackend.services.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto postQuestion(@RequestBody @Valid QuestionDto questionDto) {
        return questionService.addQuestion(questionDto);
    }

    @GetMapping()
    public AllQuestionResponseDto getAllQuestions(@RequestParam(required = false) Integer pageNumber) {
        return questionService.getAllQuestions(pageNumber);
    }
}
