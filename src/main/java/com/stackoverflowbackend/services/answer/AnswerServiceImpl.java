package com.stackoverflowbackend.services.answer;

import com.stackoverflowbackend.dtos.AnswerDto;
import com.stackoverflowbackend.exceptions.AnswerNotFoundException;
import com.stackoverflowbackend.exceptions.QuestionNotFoundException;
import com.stackoverflowbackend.exceptions.UserNotFoundException;
import com.stackoverflowbackend.mappers.AnswerMapper;
import com.stackoverflowbackend.models.Answer;
import com.stackoverflowbackend.models.Question;
import com.stackoverflowbackend.models.User;
import com.stackoverflowbackend.repositories.AnswerRepository;
import com.stackoverflowbackend.repositories.QuestionRepository;
import com.stackoverflowbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final AnswerMapper answerMapper;

    @Override
    @Transactional
    public AnswerDto postAnswer(AnswerDto answerDto) {
        Optional<User> user = userRepository.findById(answerDto.userId());
        Optional<Question> question = questionRepository.findById(answerDto.questionId());

        if (user.isEmpty()) throw new UserNotFoundException(answerDto.userId());
        if (question.isEmpty()) throw new QuestionNotFoundException(answerDto.userId());

        Answer savedAnswer = answerMapper.toEntity(answerDto);
        savedAnswer.setUser(user.get());
        savedAnswer.setQuestion(question.get());

        return answerMapper.toDtoResponseCreate(answerRepository.save(savedAnswer));
    }

    @Override
    public AnswerDto approveAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new AnswerNotFoundException(answerId));

        answer.setApprove(true);

        return answerMapper.toDtoResponseCreate(answerRepository.save(answer));
    }
}
