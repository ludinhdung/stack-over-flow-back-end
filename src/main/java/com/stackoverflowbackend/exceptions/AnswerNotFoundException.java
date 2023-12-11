package com.stackoverflowbackend.exceptions;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super(String.format("The answer with id %d was not not found", id));
    }
}
