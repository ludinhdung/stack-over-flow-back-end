package com.stackoverflowbackend.services.vote;

import com.stackoverflowbackend.dtos.VoteDto;

public interface VoteService {
    VoteDto addVoteToQuestion(VoteDto voteDto);
}
