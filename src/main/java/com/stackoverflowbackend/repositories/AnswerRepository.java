package com.stackoverflowbackend.repositories;

import com.stackoverflowbackend.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.question.id = ?1")
    List<Answer> findAllByQuestionId(Long questionId);
}