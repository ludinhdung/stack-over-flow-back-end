package com.stackoverflowbackend.repositories;

import com.stackoverflowbackend.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from Question q where q.user.id = ?1")
    Page<Question> findAllByUserId(Long userId, Pageable pageable);
}