package com.stackoverflowbackend.repositories;

import com.stackoverflowbackend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}