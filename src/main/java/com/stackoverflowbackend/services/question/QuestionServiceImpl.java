package com.stackoverflowbackend.services.question;

import com.stackoverflowbackend.dtos.AllQuestionResponseDto;
import com.stackoverflowbackend.dtos.QuestionDto;
import com.stackoverflowbackend.exceptions.UserNotFoundException;
import com.stackoverflowbackend.mappers.QuestionMapper;
import com.stackoverflowbackend.models.Question;
import com.stackoverflowbackend.models.User;
import com.stackoverflowbackend.repositories.QuestionRepository;
import com.stackoverflowbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public QuestionDto addQuestion(QuestionDto questionDto) {
        Optional<User> optionalUser = userRepository.findById(questionDto.userId());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(questionDto.userId());
        }

        Question question = questionMapper.toEntity(questionDto);
        question.setUser(optionalUser.get());

        Question createdQuestion = questionRepository.save(question);

        return questionMapper.toDtoResponseCreate(createdQuestion);
    }

    @Override
    @Transactional
    public AllQuestionResponseDto getAllQuestions(Integer pageNumber) {
        Page<Question> questionsPage = questionRepository.findAll(buildPage(pageNumber));

        List<QuestionDto> listQuestions = questionsPage.getContent().stream()
                .map(questionMapper::toDtoResponseAll).toList();

        return AllQuestionResponseDto.builder()
                .questionList(listQuestions)
                .totalPage(questionsPage.getTotalPages())
                .pageNumber(pageNumber)
                .build();
    }

    private Pageable buildPage(Integer pageNumber) {

        int queryPageNumber;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else queryPageNumber = DEFAULT_PAGE_NUMBER;

        return PageRequest.of(queryPageNumber, DEFAULT_PAGE_SIZE);
    }
}
