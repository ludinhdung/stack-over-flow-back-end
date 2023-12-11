package com.stackoverflowbackend.services.vote;

import com.stackoverflowbackend.dtos.VoteDto;
import com.stackoverflowbackend.exceptions.QuestionNotFoundException;
import com.stackoverflowbackend.exceptions.UserNotFoundException;
import com.stackoverflowbackend.mappers.VoteMapper;
import com.stackoverflowbackend.models.Question;
import com.stackoverflowbackend.models.User;
import com.stackoverflowbackend.models.Vote;
import com.stackoverflowbackend.models.VoteType;
import com.stackoverflowbackend.repositories.QuestionRepository;
import com.stackoverflowbackend.repositories.UserRepository;
import com.stackoverflowbackend.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    @Override
    @Transactional
    public VoteDto addVoteToQuestion(VoteDto voteDto) {
        Optional<User> user = userRepository.findById(voteDto.userId());
        Optional<Question> question = questionRepository.findById(voteDto.questionId());

        if (user.isEmpty()) throw new UserNotFoundException(voteDto.userId());
        if (question.isEmpty()) throw new QuestionNotFoundException(voteDto.userId());

        Question foundQuestion = question.get();

        if (voteDto.type() == VoteType.DOWNVOTE) {
            foundQuestion.setVoteCount(foundQuestion.getVoteCount() - 1);
        } else foundQuestion.setVoteCount(foundQuestion.getVoteCount() + 1);

        Vote vote = voteMapper.toEntity(voteDto);
        vote.setQuestion(question.get());
        vote.setUser(user.get());

        return voteMapper.toDtoResponseCreate(voteRepository.save(vote));
    }
}
