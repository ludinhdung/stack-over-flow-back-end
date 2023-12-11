package com.stackoverflowbackend.controllers;

import com.stackoverflowbackend.dtos.VoteDto;
import com.stackoverflowbackend.services.vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteDto aadVoteToQuestion(@RequestBody @Valid VoteDto voteDto) {
        return voteService.addVoteToQuestion(voteDto);
    }
}
