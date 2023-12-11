package com.stackoverflowbackend.repositories;

import com.stackoverflowbackend.models.Answer;
import com.stackoverflowbackend.models.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    @Sql("/multiple-data.sql")
    void testFindAllByQuestionIdSuccess() {
        List<Answer> allByQuestionId = answerRepository.findAllByQuestionId(1L);
        assertEquals(2, allByQuestionId.size());
    }

    @Test
    @Sql("/multiple-data.sql")
    void testFindAllByUserId() {
        Page<Question> allByUserId = questionRepository.findAllByUserId(1L, PageRequest.of(0, 5));
        questionRepository.findAllByUserId(1L, PageRequest.of(0, 5));
        assertEquals(1, allByUserId.getContent().size());
    }


}