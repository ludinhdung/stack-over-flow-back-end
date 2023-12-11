package com.stackoverflowbackend.repositories;

import com.stackoverflowbackend.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}