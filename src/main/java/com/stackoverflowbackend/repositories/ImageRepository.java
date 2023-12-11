package com.stackoverflowbackend.repositories;

import com.stackoverflowbackend.dtos.ImageDto;
import com.stackoverflowbackend.models.Answer;
import com.stackoverflowbackend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where i.answer = ?1")
    Image findByAnswer(Answer answer);
}