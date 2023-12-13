package com.stackoverflowbackend.services.question;

import com.stackoverflowbackend.dtos.AllQuestionResponseDto;
import com.stackoverflowbackend.dtos.AnswerDto;
import com.stackoverflowbackend.dtos.QuestionDto;
import com.stackoverflowbackend.dtos.SingleQuestionResponse;
import com.stackoverflowbackend.exceptions.ObjectNotFoundException;
import com.stackoverflowbackend.mappers.ImageMapper;
import com.stackoverflowbackend.mappers.QuestionMapper;
import com.stackoverflowbackend.models.Question;
import com.stackoverflowbackend.models.User;
import com.stackoverflowbackend.models.Vote;
import com.stackoverflowbackend.models.VoteType;
import com.stackoverflowbackend.repositories.AnswerRepository;
import com.stackoverflowbackend.repositories.ImageRepository;
import com.stackoverflowbackend.repositories.QuestionRepository;
import com.stackoverflowbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private final AnswerRepository answerRepository;

    private final ImageMapper imageMapper;

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public QuestionDto addQuestion(QuestionDto questionDto) {
        Optional<User> optionalUser = userRepository.findById(questionDto.getUserId());

        if (optionalUser.isEmpty()) {
            throw new ObjectNotFoundException("user", questionDto.getUserId());
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

    @Override
    @Transactional
    public SingleQuestionResponse getQuestionById(Long questionId, Long userId) {
        Question foundQuestion = questionRepository.findById(questionId).orElseThrow(() -> new ObjectNotFoundException("question", questionId));

        List<AnswerDto> answerResponseList = new ArrayList<>();

        Optional<Vote> optionalVote = foundQuestion.getVoteList().stream()
                .filter(vote -> vote.getUser().getId().equals(userId)).findFirst();

        QuestionDto questionDto = questionMapper.toDtoResponseAll(foundQuestion);

        if (optionalVote.isPresent()) {
            if (optionalVote.get().getType().equals(VoteType.UPVOTE)) {
                questionDto.setVote(1);
            } else questionDto.setVote(-1);
        }

        questionDto.setVote(0);

        answerRepository.findAllByQuestionId(questionId).forEach(answer -> {

                    AnswerDto answerDto = AnswerDto.builder()
                            .id(answer.getId())
                            .body(answer.getBody())
                            .file(imageMapper.toDto(imageRepository.findByAnswer(answer)))
                            .build();

                    answerResponseList.add(answerDto);
                }
        );

        return SingleQuestionResponse.builder()
                .answers(answerResponseList)
                .question(questionDto)
                .build();
    }

    @Override
    public AllQuestionResponseDto getQuestionByUserId(Long userId, Integer pageNumber) {
        Page<Question> questionsPage = questionRepository.findAllByUserId(userId, buildPage(pageNumber));

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
